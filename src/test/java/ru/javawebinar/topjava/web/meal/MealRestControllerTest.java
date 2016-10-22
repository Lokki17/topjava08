package ru.javawebinar.topjava.web.meal;

import com.sun.org.apache.regexp.internal.RE;
import org.junit.Test;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

import static org.junit.Assert.*;

public class MealRestControllerTest extends AbstractControllerTest {

    private static final String REST_MEALS = MealRestController.REST_MEALS + '/';
    private static final String START_DATE = "startDate=2015-05-30";
    private static final String END_DATE = "endDate=2015-05-30";
    private static final String START_TIME = "startTime=10:00:00";
    private static final String END_TIME = "endTime=10:00:00";

    private static final ModelMatcher<MealWithExceed> MATCHER_WITH_EXCEED = new ModelMatcher<>(MealWithExceed.class);


    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_MEALS + MEAL1_ID))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_MEALS + MEAL1_ID))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), mealService.getAll(START_SEQ));
    }

    @Test
    public void testGetAll() throws Exception {
        ResultActions actions = mockMvc.perform(get(REST_MEALS))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
        Collection<Meal> result = MealsUtil.mealsFromExceed(MATCHER_WITH_EXCEED.listFromJsonAction(actions));
        MATCHER.assertCollectionEquals(result, mealService.getAll(START_SEQ));
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("Еще один обед");
        mockMvc.perform(put(REST_MEALS + MEAL1_ID).
                contentType(MediaType.APPLICATION_JSON).
                content(JsonUtil.writeValue(updated)))
                .andExpect(status().is2xxSuccessful());
        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, START_SEQ));
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = new Meal(null, of(2016, Month.MAY, 30, 10, 0), "Завтрак", 500);
        ResultActions actions = mockMvc.perform(post(REST_MEALS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().is2xxSuccessful());
        Meal returned = MATCHER.fromJsonAction(actions);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(expected, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), mealService.getAll(START_SEQ));
    }

    @Test
    public void testGetBetween() throws Exception {
        ResultActions actions = mockMvc.perform(get(REST_MEALS + "filter" + "?" + START_DATE + "&" + END_DATE + "&" + START_TIME + "&" + END_TIME))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        Meal expected = MealsUtil.mealFromExceed(DataAccessUtils.singleResult(MATCHER_WITH_EXCEED.listFromJsonAction(actions)));
        MATCHER.assertEquals(expected, mealService.get(MEAL1_ID, START_SEQ));
    }
}