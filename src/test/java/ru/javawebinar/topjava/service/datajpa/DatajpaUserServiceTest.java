package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEAL2;
import static ru.javawebinar.topjava.MealTestData.MEAL3;
import static ru.javawebinar.topjava.MealTestData.MEAL4;
import static ru.javawebinar.topjava.MealTestData.MEAL5;
import static ru.javawebinar.topjava.MealTestData.MEAL6;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class DatajpaUserServiceTest extends AbstractUserServiceTest {


    @Test
    public void testGetWithUser() {
        List<Meal> testMeals = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
        USER.setMeals(testMeals);
        service.save(USER);
        User actual = service.getWithMeal(USER_ID);
        MealTestData.MATCHER.assertCollectionEquals(testMeals, actual.getMeals());
        UserTestData.MATCHER.assertEquals(USER, actual);
    }
}