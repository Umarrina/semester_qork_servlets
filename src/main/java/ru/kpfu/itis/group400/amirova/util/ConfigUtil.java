package ru.kpfu.itis.group400.amirova.util;

import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}