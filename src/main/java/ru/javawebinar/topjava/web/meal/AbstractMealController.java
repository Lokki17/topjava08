package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public abstract class AbstractMealController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractMealController.class);

    @Autowired
    private MealService service;

    /*public void setService(MealService service) {
        this.service = service;
    }*/

    public List<MealWithExceed> getAll(int userId) {
        LOG.info("getAll");
        return MealsUtil.getWithExceeded(
                service.getFilteredAll(userId,
                LocalDateTime.MIN,
                LocalDateTime.MAX),
                AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id, int userId) {
        LOG.info("get " + id);
        return service.get(id, userId);
    }

    public Meal save(Meal meal, int userId) {
        meal.setId(meal.getId());
        LOG.info("create " + meal);
        return service.save(meal, userId);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id, AuthorizedUser.getId());
    }

    public void update(Meal meal, int userId) {
        Meal newMeal = new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), userId);
        LOG.info("update " + newMeal);
        service.save(newMeal, userId);
    }

    public List<MealWithExceed> getFilteredAll(int id, LocalDateTime fromTime, LocalDateTime toTime) {
        return MealsUtil.getFilteredWithExceeded(
                service.getFilteredAll(id, fromTime, toTime),
                fromTime.toLocalTime(),
                toTime.toLocalTime(),
                AuthorizedUser.getCaloriesPerDay());
    }
}
