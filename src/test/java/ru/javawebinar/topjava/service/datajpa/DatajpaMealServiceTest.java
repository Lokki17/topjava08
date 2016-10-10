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
    public void testDelete() throws Exception {
        super.testDelete();
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        super.testDeleteNotFound();
    }

    @Test
    public void testSave() throws Exception {
        super.testSave();
    }

    @Test
    public void testGet() throws Exception {
        super.testGet();
    }

    @Test
    public void testGetNotFound() throws Exception {
        super.testGetNotFound();
    }

    @Test
    public void testUpdate() throws Exception {
        super.testUpdate();
        }

    @Test
    public void testNotFoundUpdate() throws Exception {
        super.testNotFoundUpdate();
        }

    @Test
    public void testGetAll() throws Exception {
        super.testGetAll();
    }

    @Test
    public void testGetBetween() throws Exception {
        super.testGetBetween();
    }

    @Test
    public void testGetWithUser(){
        MEAL1.setUser(USER);
        service.save(MEAL1, USER_ID);
        Meal actual = service.getWithUser(MEAL1.getId(), USER_ID);
        MealTestData.MATCHER.assertEquals(MEAL1, actual);
        UserTestData.MATCHER.assertEquals(USER, actual.getUser());
    }
}