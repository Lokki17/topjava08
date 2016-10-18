package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class RootMealController {
    @Autowired
    private MealService service;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(Model model) {
        model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id), AuthorizedUser.getCaloriesPerDay()));
        return "/meals";
    }

    @RequestMapping(value = "/meal/{id}", method = RequestMethod.GET)
    public String mealUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("meal", service.get(id, AuthorizedUser.id));
        return "/meal";
    }

    @RequestMapping(value = "/meal/add", method = RequestMethod.GET)
    public String mealAdd(Model model) {
        model.addAttribute("meal", new Meal());
        return "/meal";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String mealDelete(@PathVariable Integer id) {
        service.delete(id, AuthorizedUser.id);
        return "redirect:/meals";
    }

    @RequestMapping(value = {"/meals", "/meal/meals"}, method = RequestMethod.POST)
    public String mealSave(HttpServletRequest request) throws UnsupportedEncodingException {
        service.save(MealsUtil.createFromRequest(request), AuthorizedUser.id);
        return "redirect:/meals";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public String mealFiltered(HttpServletRequest request, Model model) {
        model.addAttribute("meals", MealsUtil.getFilteredWithExceedFromReq(request, service, AuthorizedUser.id, AuthorizedUser.getCaloriesPerDay()));
        return "/meals";
    }
}
