package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public class MealServiceImpl implements MealService {

    private MealRepository repository;// = new InMemoryMealRepositoryImpl();

    public void setRepository(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal save(Meal meal) {
        chekUser(meal.getUserId());
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
        chekUser(repository.get(id).getUserId());
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        chekUser(userId);
        return repository.getAll(userId);
    }

    private void chekUser(int userId){
        if (userId != AuthorizedUser.id()){
            throw new NotFoundException("Wrong id");
        }
    }
}
