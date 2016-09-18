package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public class MealServiceImpl implements MealService {

    private MealRepository repository;


    @Override
    public Meal save(Meal Meal) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Meal get(int id) {
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return null;
    }
}
