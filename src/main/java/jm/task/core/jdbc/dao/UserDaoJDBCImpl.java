package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    public UserDaoJDBCImpl() {
        //Empty constructor
    }

    public void createUsersTable() {
        String query = """
                    CREATE TABLE IF NOT EXISTS users (
                    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(45) NOT NULL,
                    lastName VARCHAR(45) NOT NULL,
                    age INT NOT NULL
                );
                """;
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
            logger.info("User table created.");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
            logger.info("User table dropped.");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = """
                    INSERT INTO users (name, lastName, age)
                    VALUES (?, ?, ?);
                """;
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            logger.info("User added.");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE id = ?;";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            logger.info("User removed.");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM users;";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
            logger.info("Users fetched.");
            ResultSet result = statement.getResultSet();
            List<User> users = new ArrayList<>();
            while (result.next()) {
                User user = new User(result.getString("name"), result.getString("lastName"), result.getByte("age"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            logger.info(e.getMessage());
            return Collections.emptyList();
        }
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE users;";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(query);
            logger.info("Table cleaned.");
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
    }
}
