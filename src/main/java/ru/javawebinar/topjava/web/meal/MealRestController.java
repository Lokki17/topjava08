package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;


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

    @Override
    public Collection<Meal> getFilteredAll(int id, LocalTime localTime, LocalTime localTime1) {
        return super.getFilteredAll(AuthorizedUser.getId(), localTime, localTime1);
    }

    @Override
    public Collection<Meal> getFilteredAll(int id, LocalDate fromDate, LocalDate toDate) {
        return super.getFilteredAll(AuthorizedUser.getId(), fromDate, toDate);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return super.getAll(AuthorizedUser.getId());
    }
}
