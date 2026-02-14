package ru.kpfu.itis.group400.amirova.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtil {

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");

                String url = System.getenv("DB_URL");
                if (url == null) url = ConfigUtil.getProperty("db.url");

                String user = System.getenv("DB_USER");
                if (user == null) user = ConfigUtil.getProperty("db.username");

                String password = System.getenv("DB_PASSWORD");
                if (password == null) password = ConfigUtil.getProperty("db.password");

                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }
}