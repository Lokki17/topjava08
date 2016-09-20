package ru.javawebinar.topjava.repository.mock;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        if (repository.containsKey(id)){
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(final int userId) {

        return repository.values().stream()
                .filter(user -> user.getUserId() == userId)
                .sorted((user1, user2) -> {
                    int i = user2.getDateTime().toLocalDate().compareTo(user1.getDateTime().toLocalDate());
                    if (i != 0) return i;
                    return user2.getDateTime().toLocalTime().compareTo(user1.getDateTime().toLocalTime());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getFilteredAll(int id, LocalTime fromTime, LocalTime toTime) {
        return repository.values().stream()
                .filter(user -> user.getUserId() == id)
                .filter(user -> TimeUtil.isBetween(user.getDateTime().toLocalTime(), fromTime, toTime))
                .sorted((user1, user2) -> {
                    int i = user2.getDateTime().toLocalDate().compareTo(user1.getDateTime().toLocalDate());
                    if (i != 0) return i;
                    return user2.getDateTime().toLocalTime().compareTo(user1.getDateTime().toLocalTime());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getFilteredAll(int id, LocalDate fromDate, LocalDate toDate) {
        return repository.values().stream()
                .filter(user -> user.getUserId() == id)
                .filter(user -> TimeUtil.isBetween(user.getDateTime().toLocalDate(), fromDate, toDate))
                .sorted((user1, user2) -> {
                    int i = user2.getDateTime().toLocalDate().compareTo(user1.getDateTime().toLocalDate());
                    if (i != 0) return i;
                    return user2.getDateTime().toLocalTime().compareTo(user1.getDateTime().toLocalTime());
                })
                .collect(Collectors.toList());
    }
}

