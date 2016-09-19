package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

public abstract class AbstractMealController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractMealController.class);

    private MealService service;

    public void setService(MealService service) {
        this.service = service;
    }

    public Collection<Meal> getAll(int userId) {
        LOG.info("getAll");
        return service.getAll(userId);
    }

    public Meal get(int id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    public Meal create(Meal meal) {
        meal.setId(null);
        LOG.info("create " + meal);
        return service.save(meal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id);
    }

    public void save(Meal meal, int userId) {
        Meal newMeal = new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), userId);
        LOG.info("update " + newMeal);
        service.save(newMeal);
    }
}
