package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {

        if (meal.isNew()) {
            checkId(meal, userId);
            meal.setId(counter.incrementAndGet());
        } else {
            checkId(repository.get(meal.getId()), userId);
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        checkId(repository.get(id), userId);
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        checkId(meal, userId);
        return meal;
    }

    @Override
    public List<Meal> getAll(final int userId) {

        return repository.values().stream()
                .filter(user -> user.getUserId() == userId)
                .sorted((user1, user2) -> user2.getDateTime().compareTo(user1.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFilteredAll(int id, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return getAll(id).stream()
                .filter(user -> TimeUtil.isBetween(user.getDateTime().toLocalDate(), fromDateTime.toLocalDate(), toDateTime.toLocalDate()))
                .collect(Collectors.toList());
    }

    private void checkId(Meal checkMeal, int userId){
        if (checkMeal == null || checkMeal.getUserId() != userId){
            throw new NotFoundException("wrong id");
        }
    }
}

