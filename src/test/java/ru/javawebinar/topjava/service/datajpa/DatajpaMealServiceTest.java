package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

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
}