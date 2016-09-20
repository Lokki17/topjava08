package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;


/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController extends AbstractMealController{

    public Meal get(int id) {
        return super.get(id, AuthorizedUser.getId());
    }

    public void delete() {
        super.delete(AuthorizedUser.getId());
    }

    public Meal save(Meal meal) {
        super.save(meal, AuthorizedUser.getId());
        return meal;
    }

    public void update(Meal meal) {
        super.save(meal, AuthorizedUser.getId());
    }

    @Override
    public Collection<Meal> getFilteredAll(int id, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return super.getFilteredAll(AuthorizedUser.getId(), fromDateTime, toDateTime);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return super.getAll(AuthorizedUser.getId());
    }
}
