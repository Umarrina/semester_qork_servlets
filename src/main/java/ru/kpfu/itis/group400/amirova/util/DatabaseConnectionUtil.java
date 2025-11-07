package ru.kpfu.itis.group400.amirova.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtil {

    private static Connection connection;

    private static final String URL = ConfigUtil.getProperty("db.url");
    private static final String USERNAME = ConfigUtil.getProperty("db.username");
    private static final String PASSWORD = ConfigUtil.getProperty("db.password");

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                return DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}
