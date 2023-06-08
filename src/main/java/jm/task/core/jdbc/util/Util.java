package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final Logger LOGGER = Logger.getLogger(Util.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/kata";
    private static final String USERNAME = "idea";
    private static final String PASSWORD = "R*3qY#eU#d57Vu";

    private Util() {
        throw new IllegalStateException("Utility class");
    }

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            LOGGER.info("Database connected!");
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
