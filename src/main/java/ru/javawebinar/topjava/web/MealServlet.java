package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.AbstractMealController;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    //@Autowired
    private MealRestController mealRestController;

    private static ConfigurableApplicationContext appCtx;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")),
                AuthorizedUser.getId());

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);

        mealRestController.save(meal);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (request.getParameter("user") != null && !request.getParameter("user").isEmpty()){
            AuthorizedUser.setId(Integer.parseInt(request.getParameter("user")));}

        if (action == null) {
            LOG.info("getAll");

            request.setAttribute("mealList",
                    MealsUtil.getWithExceeded(mealRestController.getAll(AuthorizedUser.getId()), AuthorizedUser.getCaloriesPerDay()));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);

        } else if ("filterDate".equals(action)){
            String fromDateStr = request.getParameter("fromDate");
            String toDateStr = request.getParameter("toDate");

            request.setAttribute("mealList",
                    MealsUtil.getWithExceeded(mealRestController.getFilteredAll(AuthorizedUser.getId(),
                            !fromDateStr.isEmpty() ? TimeUtil.toLocalDate(fromDateStr) : LocalDate.MIN,
                            !toDateStr.isEmpty() ? TimeUtil.toLocalDate(toDateStr) : LocalDate.MAX),
                            AuthorizedUser.getCaloriesPerDay()));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);

        } else if ("filterTime".equals(action)){
            String fromTimeStr = request.getParameter("fromTime");
            String toTimeStr = request.getParameter("toTime");

            request.setAttribute("mealList",
                    MealsUtil.getWithExceeded(mealRestController.getFilteredAll(AuthorizedUser.getId(),
                            !fromTimeStr.isEmpty() ? TimeUtil.toLocalTime(fromTimeStr.substring(0, 5)) : LocalTime.MIN,
                            !toTimeStr.isEmpty() ?  TimeUtil.toLocalTime(toTimeStr.substring(0, 5)) : LocalTime.MAX),
                            AuthorizedUser.getCaloriesPerDay()));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);

            mealRestController.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000, AuthorizedUser.getId()) :

                    mealRestController.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }
}
