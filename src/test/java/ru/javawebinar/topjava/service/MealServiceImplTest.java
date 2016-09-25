package ru.javawebinar.topjava.service;


import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static ru.javawebinar.topjava.MealTestData.MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"})

@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceImplTest {

    @Autowired
    MealService mealService;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal testMeal = mealService.get(MEAL_ID, USER_ID);
        MATCHER.assertEquals(testMeal, MEAL1);
    }

    @Test
    public void testDelete() throws Exception {
        mealService.delete(ADMIN_MEAL_ID, ADMIN_ID);
        MATCHER.assertCollectionEquals(
                Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6),
                mealService.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() {
        mealService.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testWrongUserMealDelete() {
        mealService.delete(MEAL_ID, ADMIN_ID);
    }
    @Test(expected = NotFoundException.class)
    public void testWrongUserMealGet() {
        mealService.get(MEAL_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testWrongUserMealUpdate() {
        mealService.update(new Meal(MEAL1.getId(), MEAL1.getDateTime(), "hack meal", MEAL1.getCalories() + 1000), ADMIN_ID);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        Collection<Meal> getBetweenMeals = mealService.getBetweenDates(
                LocalDateTime.of(2015, Month.MAY, 30, 0, 0).toLocalDate(),
                LocalDateTime.of(2015, Month.MAY, 30, 0, 0).toLocalDate(),
                USER_ID);
        MATCHER.assertCollectionEquals(getBetweenMeals, Arrays.asList(MEAL1, MEAL2, MEAL3));
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(mealService.getAll(USER_ID),
                Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6));
    }

    @Test
    public void testGetAdninMealAll() throws Exception {
        MATCHER.assertCollectionEquals(mealService.getAll(ADMIN_ID),
                Collections.singletonList(MEAL7));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal newMeal = new Meal(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories() - 1);
        Meal updatedMeal = mealService.update(newMeal, USER_ID);
        MATCHER.assertEquals(newMeal, updatedMeal);
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(null, MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        Meal updateMeal = mealService.save(newMeal, USER_ID);
        MATCHER.assertEquals(newMeal, updateMeal);
    }
}