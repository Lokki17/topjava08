package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Profile("hsqldb")
@Repository
public class JdbcHsqldbMealRepositoryImpl extends JdbcMealRepositoryImpl {
    public JdbcHsqldbMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM meals WHERE user_id=?  AND date_time BETWEEN ? AND ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, startDate.format(TimeUtil.DATE_TIME_HSQLDB_FORMATTER), endDate.format(TimeUtil.DATE_TIME_HSQLDB_FORMATTER));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories())
                .addValue("date_time", meal.getDateTime().format(TimeUtil.DATE_TIME_HSQLDB_FORMATTER))
                .addValue("user_id", userId);

        if (meal.isNew()) {
            Number newId = insertMeal.executeAndReturnKey(map);
            meal.setId(newId.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("" +
                            "UPDATE meals " +
                            "   SET description=:description, calories=:calories, date_time=:date_time " +
                            " WHERE id=:id AND user_id=:user_id"
                    , map) == 0) {
                return null;
            }
        }
        return meal;
    }
}
