package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Profile("hsqldb")
@Repository
public class JdbcHsqldbMealRepositoryImpl extends JdbcMealRepositoryImpl {
    public JdbcHsqldbMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Date getRightDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
