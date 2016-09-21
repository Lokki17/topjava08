package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.to.MealWithExceed;

import java.time.LocalDateTime;
import java.util.List;


/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController extends AbstractMealController{

    public Meal get(int id) {
        return super.get(id, AuthorizedUser.getId());
    }

    public void delete(int id) {
        super.delete(id, AuthorizedUser.getId());
    }

    public Meal save(Meal meal) {
        return super.save(meal, AuthorizedUser.getId());
    }

/*
    public void update(Meal meal) {
        super.save(meal, AuthorizedUser.getId());
    }
*/

    @Override
    public List<MealWithExceed> getFilteredAll(int id, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return super.getFilteredAll(AuthorizedUser.getId(), fromDateTime, toDateTime);
    }
}
