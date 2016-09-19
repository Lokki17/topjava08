package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

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

    //private MealRepository repository;

    private MealServiceImpl mealService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        mealService = new MealServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")),
                AuthorizedUser.id());

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);

        mealService.save(meal);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (request.getParameter("user") != null && !request.getParameter("user").isEmpty()){
            AuthorizedUser.id = Integer.parseInt(request.getParameter("user"));}

        if (action == null) {
            LOG.info("getAll");

            request.setAttribute("mealList",
                    MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);

        } else if ("filterDate".equals(action)){

            LocalDate fromDate = !request.getParameter("fromDate").isEmpty() ? TimeUtil.toLocalDate(request.getParameter("fromDate")) :
                    LocalDate.MIN;
            LocalDate toDate = !request.getParameter("fromDate").isEmpty() ? TimeUtil.toLocalDate(request.getParameter("toDate")) :
                    LocalDate.MAX;

/*            LocalTime fromTime = !request.getParameter("fromTime").isEmpty() ? TimeUtil.toLocalTime(request.getParameter("fromTime")) :
                    LocalTime.MIN;
            LocalTime toTime = !request.getParameter("toTime").isEmpty() ?  TimeUtil.toLocalTime(request.getParameter("toTime")) :
                    LocalTime.MAX;*/

/*            request.setAttribute("mealList",
                    MealsUtil.getFilteredWithExceeded(mealService.getAll(AuthorizedUser.id()), fromTime, toTime, AuthorizedUser.getCaloriesPerDay()));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);*/
            request.setAttribute("mealList",
                    MealsUtil.getFilteredWithExceeded(mealService.getAll(AuthorizedUser.id()), fromDate, toDate, AuthorizedUser.getCaloriesPerDay()));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if ("filterTime".equals(action)){

/*            LocalDate fromDate = !request.getParameter("fromDate").isEmpty() ? TimeUtil.toLocalDate(request.getParameter("fromDate")) :
                    LocalDate.MIN;
            LocalDate toDate = !request.getParameter("fromDate").isEmpty() ? TimeUtil.toLocalDate(request.getParameter("toDate")) :
                    LocalDate.MAX;*/

            LocalTime fromTime = !request.getParameter("fromTime").isEmpty() ? TimeUtil.toLocalTime(request.getParameter("fromTime")) :
                    LocalTime.MIN;
            LocalTime toTime = !request.getParameter("toTime").isEmpty() ?  TimeUtil.toLocalTime(request.getParameter("toTime")) :
                    LocalTime.MAX;

/*            request.setAttribute("mealList",
                    MealsUtil.getFilteredWithExceeded(mealService.getAll(AuthorizedUser.id()), fromTime, toTime, AuthorizedUser.getCaloriesPerDay()));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);*/
            request.setAttribute("mealList",
                    MealsUtil.getFilteredWithExceeded(mealService.getAll(AuthorizedUser.id()), fromTime, toTime, AuthorizedUser.getCaloriesPerDay()));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);

            mealService.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000, AuthorizedUser.id()) :

                    mealService.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
