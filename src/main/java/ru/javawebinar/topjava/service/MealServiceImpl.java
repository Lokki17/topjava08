package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;// = new InMemoryMealRepositoryImpl();

    @Override
    public Meal save(Meal meal, int userId) {
        ExceptionUtil.checkNotFound(repository.save(meal, userId), meal.toString());
        return meal;
    }

    @Override
    public void delete(int id, int userId) {
        ExceptionUtil.checkNotFound(repository.delete(id, userId), Integer.toString(userId));
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id, userId);
        ExceptionUtil.checkNotFound(meal, meal.toString());
        return meal;
    }

    @Override
    public List<Meal> getFilteredAll(int id, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        List<Meal> meals = repository.getFilteredAll(id, fromDateTime, toDateTime);
        if (meals.isEmpty()){
            return Collections.emptyList();
        }
        return meals;
    }
}
