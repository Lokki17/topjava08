package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private static final RowMapper<UserRole> ROLE_MAPPER = BeanPropertyRowMapper.newInstance(UserRole.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay())
                .addValue("roles", user.getRoles());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            user.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
        }
        setRoles(user, getUsersRoles());
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        User user = DataAccessUtils.singleResult(jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id));
        if (user != null) {
            setRoles(user, getUsersRoles());
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        User user = DataAccessUtils.singleResult(jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email));
        if (user != null) {
            setRoles(user, getUsersRoles());
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        List<UserRole> userRoles = getUsersRoles();
        users.forEach(user -> setRoles(user, userRoles));
        return users;
    }

    private User setRoles(User user, List<UserRole> userRoles){
        user.setRoles(userRoles.stream()
                .filter(userRole -> Objects.equals(userRole.getUserId(), user.getId()))
                .map(u -> Role.valueOf(u.getRole()))
                .collect(Collectors.toSet()));
        return user;
    }

    private List<UserRole> getUsersRoles(){
        return jdbcTemplate.query("SELECT role, user_id FROM user_roles", ROLE_MAPPER);
    }

    private static class UserRole {
        Integer user_Id;
        String role;

        public UserRole() {
        }

        public Integer getUserId() {
            return user_Id;
        }

        public String getRole() {
            return role;
        }

        public void setUserId(Integer userId) {
            this.user_Id = userId;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

/*    в @NamedEntityGraph нужны только проперти к которым нужен JOIN, остальные не нужны
    gkislin [5:44 PM]
    ага. и тоже хорошо бы чз константу
    и заюзать в datajpa (edited)
            [5:46]
    JdbcUserRepositoryImpl:102 - тебе тут IDEA ничего не написала?
    gkislin [5:53 PM]
    при update- ты все роли у юзера которые пришли- потерял
    [6:03]
    для jdbc по прежнему entityManagerFactory не нужен
    gkislin [6:04 PM]
    транзакции - не делал?
    тесты и приложение работают:)
    кроме - при добавлении еды ссылка поплыла- http://localhost:8080/topjava/meal/meals
    когда работаем с едой, все должно быть от meals
    [6:09 PM]
    если бы не работали тесты и приложение - я бы даже на проверку слать не стал
    gkislin [6:10 PM]
    я так сделал, проще всего
    <base href=
*/
}


