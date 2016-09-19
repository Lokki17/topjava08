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
        //repository = new InMemoryMealRepositoryImpl();
        mealService = new MealServiceImpl();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
/*        if (request.getParameter("user") != null){
            AuthorizedUser.id = Integer.parseInt(request.getParameter("user"));}*/

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")),
                AuthorizedUser.id());

        LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        //repository.save(meal);
        mealService.save(meal);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (request.getParameter("user") != null && !request.getParameter("user").isEmpty()){
            AuthorizedUser.id = Integer.parseInt(request.getParameter("user"));}

        if (action == null) {
            LOG.info("getAll");
/*
            request.setAttribute("mealList",
                    MealsUtil.getWithExceeded(repository.getAll(AuthorizedUser.id()), MealsUtil.DEFAULT_CALORIES_PER_DAY));
*/
            request.setAttribute("mealList",
                    MealsUtil.getWithExceeded(mealService.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);

        } else if ("filter".equals(action)){

            LocalDate fromDate = !request.getParameter("fromDate").isEmpty() ? TimeUtil.toLocalDate(request.getParameter("fromDate")) :
                    LocalDate.MIN;
            //System.out.println(fromDate);
            LocalDate toDate = !request.getParameter("fromDate").isEmpty() ? TimeUtil.toLocalDate(request.getParameter("toDate")) :
                    LocalDate.MAX;
            //System.out.println(toDate);

            LocalTime fromTime = !request.getParameter("fromTime").isEmpty() ? TimeUtil.toLocalTime(request.getParameter("fromTime")) :
                    LocalTime.MIN;
            LocalTime toTime = !request.getParameter("toTime").isEmpty() ?  TimeUtil.toLocalTime(request.getParameter("toTime")) :
                    LocalTime.MAX;

            //LocalDateTime fromDateTime = LocalDateTime.of(fromDate, fromTime);
            //LocalDateTime toDateTime = LocalDateTime.of(fromDate, fromTime);

            request.setAttribute("mealList",
                    MealsUtil.getFilteredWithExceeded(mealService.getAll(AuthorizedUser.id()), fromTime, toTime, AuthorizedUser.getCaloriesPerDay()));
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        }

        else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            //repository.delete(id);
            mealService.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000, AuthorizedUser.id()) :
                    //repository.get(getId(request));
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
