package ru.javawebinar.topjava.service.datajpa;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.ACTIVE_REPOSITORY})
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
}