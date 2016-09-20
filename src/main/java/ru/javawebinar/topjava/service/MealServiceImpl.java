package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;// = new InMemoryMealRepositoryImpl();

/*    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }*/

    @Override
    public Meal save(Meal meal, int userId) {
        repository.save(meal, userId);
        return meal;
    }

    @Override
    public void delete(int id, int userId) {
        repository.delete(id, userId);
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id, userId);
        if (meal == null) {
            throw new NotFoundException("Empty meal");
        }
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Collection<Meal> meals = repository.getAll(userId);
        if (meals.isEmpty()){
            return Collections.EMPTY_LIST;
            //throw new NotFoundException("Empty list");
        }
        return meals;
    }

    @Override
    public Collection<Meal> getFilteredAll(int id, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Collection<Meal> meals = repository.getFilteredAll(id, fromDateTime, toDateTime);
        if (meals.isEmpty()){
            return Collections.EMPTY_LIST;
            //throw new NotFoundException("Empty list");
        }
        return meals;
    }

    private void chekUser(int userId) {
        if (userId != AuthorizedUser.getId()) {
            throw new NotFoundException("Wrong id");
        }
    }
}
