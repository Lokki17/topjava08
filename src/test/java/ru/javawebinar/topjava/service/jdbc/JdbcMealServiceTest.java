package ru.javawebinar.topjava.service.jdbc;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.JDBC})
public class JdbcMealServiceTest extends AbstractMealServiceTest{
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
}