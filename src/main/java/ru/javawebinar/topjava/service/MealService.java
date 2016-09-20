package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
    Meal save(Meal Meal);

    void delete(int id);

    Meal get(int id);

    Collection<Meal> getAll(int userId);

    Collection<Meal> getFilteredAll(int id, LocalTime fromTime, LocalTime toTime);

    Collection<Meal> getFilteredAll(int id, LocalDate fromDate, LocalDate toDate);
}
