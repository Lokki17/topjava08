package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    @RequestMapping(value = "/meals/add", method = RequestMethod.GET)
    public String mealAdd(Model model) {
        model.addAttribute("meal", new Meal());
        return "/meal";
    }

    @RequestMapping(value = "/meals/delete/{id}", method = RequestMethod.GET)
    public String mealDelete(@PathVariable Integer id) {
        service.delete(id, AuthorizedUser.id);
        return "redirect:/meals";
    }

    @RequestMapping(value = {"/meals"}, method = RequestMethod.POST)
    public String mealSave(HttpServletRequest request) throws UnsupportedEncodingException {
        service.save(MealsUtil.createFromRequest(request), AuthorizedUser.id);
        return "redirect:/meals";
    }

    @RequestMapping(value = "meals/filter", method = RequestMethod.GET)
    public String mealFiltered(HttpServletRequest request, Model model) {
        model.addAttribute("meals", getFilteredWithExceedFromReq(request, service, AuthorizedUser.id, AuthorizedUser.getCaloriesPerDay()));
        return "/meals";
    }

    private static List<MealWithExceed> getFilteredWithExceedFromReq(HttpServletRequest request, MealService service, int userId, int calories) {

        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
        return MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(startDate != null ? startDate : TimeUtil.MIN_DATE, endDate != null ? endDate : TimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                calories);
    }

    private static String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }
}
