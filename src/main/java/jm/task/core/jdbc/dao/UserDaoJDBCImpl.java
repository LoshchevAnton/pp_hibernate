package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_TABLE =
            """
            CREATE TABLE IF NOT EXISTS users (
            id INT AUTO_INCREMENT PRIMARY KEY,
            name VARCHAR(250) NOT NULL,
            lastName VARCHAR(250) NOT NULL,
            age INT NOT NULL)
            """;
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private static final String INSERT_USER =
            """
            INSERT INTO users (name, lastName, age)
            VALUES (?, ?, ?)
            """;
    private static final String DELETE_USER =
            """
            DELETE FROM users
            WHERE id = ?;
            """;
    private static final String SELECT_USERS =
            """
            SELECT name, lastName, age
            FROM users
            """;
    private static final String DELETE_ALL = "DELETE FROM users";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement()) {
            statement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement()) {
            statement.execute(DROP_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getConnection();
             PreparedStatement statement = conn.prepareStatement(INSERT_USER);) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_USER);) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = Util.getConnection();
            Statement statement = conn.createStatement()) {
            ResultSet queryResult = statement.executeQuery(SELECT_USERS);
            while (queryResult.next()) {
                String name = queryResult.getString("name");
                String lastName = queryResult.getString("lastName");
                byte age = queryResult.getByte("age");
                User currentUser = new User(name, lastName, age);
                users.add(currentUser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection conn = Util.getConnection();
             Statement statement = conn.createStatement()) {
            statement.executeUpdate(DELETE_ALL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
