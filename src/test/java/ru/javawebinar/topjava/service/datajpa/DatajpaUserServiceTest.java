package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.UserTestData.MATCHER;
import static ru.javawebinar.topjava.MealTestData.MEAL2;
import static ru.javawebinar.topjava.MealTestData.MEAL3;
import static ru.javawebinar.topjava.MealTestData.MEAL4;
import static ru.javawebinar.topjava.MealTestData.MEAL5;
import static ru.javawebinar.topjava.MealTestData.MEAL6;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class DatajpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void testSave() throws Exception {
        super.testSave();
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateMailSave() throws Exception {
        super.testDuplicateMailSave();
    }

    @Test
    public void testDelete() throws Exception {
        super.testDelete();
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        super.testNotFoundDelete();
    }

    @Test
    public void testGet() throws Exception {
        super.testGet();
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        super.testGetNotFound();
    }

    @Test
    public void testGetByEmail() throws Exception {
        super.testGetByEmail();
    }

    @Test
    public void testGetAll() throws Exception {
        super.testGetAll();
    }

    @Test
    public void testUpdate() throws Exception {
        super.testUpdate();
    }

    @Test
    public void testGetWithUser() {
        USER.setMeals(Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6));

        service.save(USER);
        User actual = service.get(USER_ID);
        MATCHER.assertEquals(USER, actual);
    }
}