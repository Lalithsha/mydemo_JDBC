package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
// import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Repository
public class UserRepositoryImp implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepositoryImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createUser(User user) {
        String query = "INSERT INTO user (username, password, admin) VALUES (?, ?, ?)";
        int result = jdbcTemplate.update(query, user.getUsername(), user.getPassword(), user.getAdmin());
        return result;
    }

    @Override
    public int updateUser(User user) {
        String query = "UPDATE user SET username = ?, password = ?, admin = ? WHERE id = ?";
        int result = jdbcTemplate.update(query, user.getUsername(), user.getPassword(), user.getAdmin());
        return result;
    }

    @Override
    public int deleteUser(Long id) {
        String query = "DELETE FROM user WHERE id = ?";
        int result = jdbcTemplate.update(query, id);
        return result;
    }

    @Override
    public User getUserById(Long id) {
        /*
         * String query = "SELECT * FROM user WHERE id = ?";
         * return jdbcTemplate.queryForObject(query, new Object[] { id }, new
         * UserMapper());
         */
        String query = "SELECT * FROM user WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new UserMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT * FROM user";
        return jdbcTemplate.query(query, new UserMapper());
    }

    @Override
    public int changeAdmin(Long id, boolean isAdmin) {
        String query = "UPDATE user SET admin = ? WHERE id = ?";
        int result = jdbcTemplate.update(query, isAdmin, id);
        return result;
    }

    @Override
    public boolean isUserAdmin(Long id) {
        String query = "SELECT admni from user WHERE id = ?";
        Boolean result = jdbcTemplate.queryForObject(query, Boolean.class, id);
        return result != null && result;
    }

    public static final class UserMapper implements RowMapper<User> {

        @Override
        @Nullable
        public User mapRow(ResultSet rs, int arg1) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setAdmin(rs.getBoolean("admin"));
            return user;
        }

    }

}
