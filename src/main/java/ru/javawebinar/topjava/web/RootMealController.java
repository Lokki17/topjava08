package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/meals")
public class RootMealController {
    @Autowired
    private MealService service;

    @RequestMapping(method = RequestMethod.GET)
    public String meals(Model model){
        model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

/*    @RequestMapping(name = "", method = RequestMethod.GET)
    public String */

/*    @RequestMapping(value = "/meal/{id}", method = RequestMethod.POST)
    public String meals(@Param("id") HttpServletRequest request){
        model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id), MealsUtil.DEFAULT_CALORIES_PER_DAY));
        return "meals";
    }*/
}
