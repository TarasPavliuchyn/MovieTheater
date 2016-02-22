package com.epam.spring.theater.dao.impl;

import com.epam.spring.theater.dao.UserDao;
import com.epam.spring.theater.model.User;
import com.epam.spring.theater.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private static final String INSERT_QUERY = "INSERT INTO user (email, password, full_name, birthday, user_role) VALUES (?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE  user SET password=?, full_name=?, birthday=?, user_role=? WHERE email=?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM user";
    private static final String FIND_EXIST_USER = "SELECT * FROM user  WHERE email=?";
    private static final String FIND_USER_BY_NAME = "SELECT * FROM user  WHERE full_name=?";
    private static final String FIND_USER_BY_ID = "SELECT * FROM user  WHERE user_id=?";
    private static final String DELETE_QUERY = "DELETE from user WHERE email=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) {
        Object[] params = {user.getEmail(), user.getPassword(), user.getFullName(), new Date(user.getBirthDay().getTime()), user.getRole().name()};
        try {
            jdbcTemplate.queryForObject(FIND_EXIST_USER, new Object[]{user.getEmail()}, new UserRowMapper());
            throw new UserExistException("User already exists");
        } catch (EmptyResultDataAccessException e) {
            jdbcTemplate.update(INSERT_QUERY, params);
        }
        return user;
    }

    @Override
    public User update(User user) {
        Object[] params = {user.getPassword(), user.getFullName(), new Date(user.getBirthDay().getTime()), user.getRole().name(), user.getEmail()};
        jdbcTemplate.update(UPDATE_QUERY, params);
        return user;
    }

    @Override
    public User find(Integer userId) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(FIND_USER_BY_ID, new Object[]{userId}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }


    @Override
    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(FIND_EXIST_USER, new Object[]{email}, new UserRowMapper());
    }

    @Override
    public User getUsersByName(String name) {
        return jdbcTemplate.queryForObject(FIND_USER_BY_NAME, new Object[]{name}, new UserRowMapper());
    }

    @Override
    public List<User> findAll() {
        List<User> users = jdbcTemplate.query(SELECT_ALL_QUERY, new UserRowMapper());
        return users;
    }

    @Override
    public void remove(User model) {
        jdbcTemplate.update(DELETE_QUERY, new Object[]{model.getEmail()});
    }

    static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUserId(resultSet.getInt("user_id"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setFullName(resultSet.getString("full_name"));
            user.setBirthDay(resultSet.getDate("birthday"));
            user.setRole(UserRole.valueOf(resultSet.getString("user_role")));
            return user;
        }
    }
}