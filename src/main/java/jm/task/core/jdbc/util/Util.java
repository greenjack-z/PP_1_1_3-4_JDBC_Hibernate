package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
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

    public static Session getSession() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();

        properties.put(AvailableSettings.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        properties.put(AvailableSettings.URL, URL);
        properties.put(AvailableSettings.USER, USERNAME);
        properties.put(AvailableSettings.PASS, PASSWORD);

        configuration.setProperties(properties);
        configuration.addAnnotatedClass(User.class);
        return configuration.buildSessionFactory().openSession();
    }
}
