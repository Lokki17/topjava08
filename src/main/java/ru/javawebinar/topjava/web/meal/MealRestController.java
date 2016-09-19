package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public class MealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    private MealService service;

    public void setService(MealService service) {
        this.service = service;
    }

    public Meal save(Meal meal) {
        chekUser(meal.getUserId());
        service.save(meal);
        return meal;
    }

    public void delete(int id) {
        chekUser(service.get(id).getUserId());
        service.delete(id);
    }

    public Meal get(int id) {
        chekUser(service.get(id).getUserId());
        return service.get(id);
    }

    public Collection<Meal> getAll(int userId) {
        chekUser(userId);
        return service.getAll(userId);
    }

    private void chekUser(int userId){
        if (userId != AuthorizedUser.getId()){
            throw new NotFoundException("Wrong id");
        }
    }
}
