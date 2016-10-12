package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Profile("postgres")
@Repository
public class JdbcPostgresMealRepositoryImpl extends JdbcMealRepositoryImpl {

    public JdbcPostgresMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public LocalDateTime getRightDate(LocalDateTime dateTime) {
        return dateTime;
    }

}
