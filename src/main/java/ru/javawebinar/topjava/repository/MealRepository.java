package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(Meal Meal);

    boolean delete(int id);

    Meal get(int id);

    Collection<Meal> getAll(int userId);

    Collection<Meal> getFilteredAll(int id, LocalTime fromTime, LocalTime toTime);

    Collection<Meal> getFilteredAll(int id, LocalDate fromDate, LocalDate toDate);
}
