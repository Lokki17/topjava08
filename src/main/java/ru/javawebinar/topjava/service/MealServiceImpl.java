package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public class MealServiceImpl implements MealService {

    //@Autowired
    private MealRepository repository;// = new InMemoryMealRepositoryImpl();

    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal) {
        repository.save(meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        chekUser(repository.get(id).getUserId());
        repository.delete(id);
    }

    @Override
    public Meal get(int id) {
        Meal meal = repository.get(id);
        if (meal == null) {
            throw new NotFoundException("Empty meal");
        }
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Collection<Meal> meals = repository.getAll(userId);
        if (meals.isEmpty()){
            throw new NotFoundException("Empty list");
        }
        return meals;
    }

    @Override
    public Collection<Meal> getFilteredAll(int id, LocalTime fromTime, LocalTime toTime) {
        Collection<Meal> meals = repository.getFilteredAll(id, fromTime, toTime);
        if (meals.isEmpty()){
            throw new NotFoundException("Empty list");
        }
        return meals;
    }

    @Override
    public Collection<Meal> getFilteredAll(int id, LocalDate fromDate, LocalDate toDate) {
        Collection<Meal> meals = repository.getFilteredAll(id, fromDate, toDate);
        if (meals.isEmpty()){
            throw new NotFoundException("Empty list");
        }
        return meals;
    }

    private void chekUser(int userId) {
        if (userId != AuthorizedUser.getId()) {
            throw new NotFoundException("Wrong id");
        }
    }
}
