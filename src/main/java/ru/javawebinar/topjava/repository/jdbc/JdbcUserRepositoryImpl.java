package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
                .addValue("caloriesPerDay", user.getCaloriesPerDay());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            user.setId(newKey.intValue());
        } else {
            deleteRoles(user.getId());
            insertRoles(user);
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
            //namedParameterJdbcTemplate.update("UPDATE user_roles SET user_id=:id, role=:roles", map);
        }
        user.setRoles(getUserRoles(user.getId()));
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
            user.setRoles(getUserRoles(user.getId()));
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        User user = DataAccessUtils.singleResult(jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email));
        if (user != null) {
            user.setRoles(getUserRoles(user.getId()));
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        users.forEach(user -> setRoles(user, getUsersRoles()));
        return users;
    }

    private User setRoles(User user, List<UserRole> userRoles) {
        user.setRoles(userRoles.stream()
                .filter(userRole -> Objects.equals(userRole.getUserId(), user.getId()))
                .map(u -> Role.valueOf(u.getRole()))
                .collect(Collectors.toSet()));
        return user;
    }

    private List<UserRole> getUsersRoles() {
        return jdbcTemplate.query("SELECT role, user_id FROM user_roles", ROLE_MAPPER);
    }

    private Set<Role> getUserRoles(int userId) {
        List<String> getedRoles = jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id=?", (rs, rowNum) -> {
            return rs.getString("role");
        }, userId);
        return getedRoles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }

    private void insertRoles(final User user){

        String sql = "INSERT INTO user_roles (user_id, role) VALUES (?, ?)";
        Iterator<Role> iterator = user.getRoles().iterator();


        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, user.getId());
                ps.setString(2, iterator.next().toString());
            }

            @Override
            public int getBatchSize() {
                return user.getRoles().size();
            }
        });
    }

    private void deleteRoles(int userId){
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", userId);
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

    gkislin [6:04 PM]
    транзакции - не делал?
    тесты и приложение работают:)

*/
}


