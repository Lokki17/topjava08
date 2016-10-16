package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Controller
//@RequestMapping(value = "/meals")
public class RootMealController {
    @Autowired
    private MealService service;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(HttpServletRequest request, Model model) {
        model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id), AuthorizedUser.getCaloriesPerDay()));
        return "/meals";
    }

    @RequestMapping(value = "meal/{id}", method = RequestMethod.GET)
    public String mealUpdate(@PathVariable Integer id, Model model) {
        model.addAttribute("meal", service.get(id, AuthorizedUser.id));
        return "/meal";
    }

    @RequestMapping(value = "meal/add", method = RequestMethod.GET)
    public String mealAdd(Model model) {
        model.addAttribute("meal", new Meal());
        return "/meal";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String mealDelete(@PathVariable Integer id) {
        service.delete(id, AuthorizedUser.id);
        return "redirect:/meals";
    }

    @RequestMapping(value = {"/meals", "meal/meals"}, method = RequestMethod.POST)
    public String mealSave(HttpServletRequest request) throws UnsupportedEncodingException {
        service.save(MealsUtil.createFromRequest(request), AuthorizedUser.id);
        return "redirect:/meals";
    }

    @RequestMapping(value = "filter", method = RequestMethod.GET)
    public String mealFiltered(HttpServletRequest request, Model model) {
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
        model.addAttribute("meals", MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(startDate != null ? startDate : TimeUtil.MIN_DATE, endDate != null ? endDate : TimeUtil.MAX_DATE, AuthorizedUser.id),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        ));
        return "meals";
    }

    private static String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }
}
