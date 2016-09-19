package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;


/**
 * GKislin
 * 06.03.2015.
 */
public class MealRestController extends AbstractMealController{

    public Meal get() {
        return super.get(AuthorizedUser.getId());
    }

    public void delete() {
        super.delete(AuthorizedUser.getId());
    }

    public void save(Meal meal) {
        super.save(meal, AuthorizedUser.getId());
    }
}
