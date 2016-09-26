package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static BeanPropertyRowMapper ROW_MEAL_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    public JdbcMealRepositoryImpl(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("MEALS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("calories", meal.getCalories())
                .addValue("dateTime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("userId", userId)
                .addValue("id", meal.getId());
        if (meal.isNew()){
            int mealId = (Integer) simpleJdbcInsert.executeAndReturnKey(map);
            meal.setId(mealId);
            return meal;
        } else {
            int result = namedParameterJdbcTemplate.update(
                    "UPDATE meals SET calories=:calories, datetime=:dateTime, " +
                            "description=:description WHERE userId=:userId AND id=:id", map);
            return result == 0 ? null : meal;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals where id=? AND userid=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meal = jdbcTemplate.query("SELECT * FROM meals WHERE userid=? AND id=?", ROW_MEAL_MAPPER, userId, id);
        return DataAccessUtils.singleResult(meal);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE userId=? ORDER BY id", ROW_MEAL_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE userId=? AND datetime>=? " +
                "AND datetime<=? ORDER BY id", ROW_MEAL_MAPPER, userId, startDate, endDate);
    }
}
