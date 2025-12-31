package com.endava.ai.core.config;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream is = ConfigManager.class
                .getClassLoader()
                .getResourceAsStream("framework.properties")) {

            if (is == null) {
                throw new RuntimeException("framework.properties not found on classpath");
            }
            PROPERTIES.load(is);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load framework.properties", e);
        }
    }

    private ConfigManager() {
    }

    public static String get(String key, String defaultValue) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) return defaultValue == null ? null : defaultValue.trim();
        value = value.trim();
        return value.isEmpty() ? (defaultValue == null ? null : defaultValue.trim()) : value;
    }

    public static String require(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Missing config key: " + key);
        }
        value = value.trim();
        if (value.isEmpty()) {
            throw new IllegalStateException("Config key is empty: " + key);
        }
        return value;
    }

}
