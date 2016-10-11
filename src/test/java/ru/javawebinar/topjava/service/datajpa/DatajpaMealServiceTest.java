package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class DatajpaMealServiceTest extends AbstractMealServiceTest{

    @Test
    public void testGetWithUser(){
        MEAL1.setUser(USER);
        service.save(MEAL1, USER_ID);
        Meal actual = service.getWithUser(MEAL1.getId(), USER_ID);
        MealTestData.MATCHER.assertEquals(MEAL1, actual);
        UserTestData.MATCHER.assertEquals(USER, actual.getUser());
    }
}