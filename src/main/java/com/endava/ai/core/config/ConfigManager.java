package com.endava.ai.core.config;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        try (InputStream is = ConfigManager.class
                .getClassLoader()
                .getResourceAsStream("framework.properties")) {

            if (is == null) {
                throw new RuntimeException("framework.properties not found on classpath");
            }
            properties.load(is);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load framework.properties", e);
        }
    }

    private ConfigManager() {
        // prevent instantiation
    }

    public static String getApiBaseUrl() {
        return require("base.url.api");
    }

    @SuppressWarnings("unused")
    public static String getAuthToken() {
        return require("auth.token");
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

     public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(require(key));
    }

    public static int getInt(String key) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (Exception e) {
            throw new RuntimeException(
                    "Missing or invalid integer config key: " + key, e
            );
        }
    }

    public static String require(String key) {
        String value = properties.getProperty(key);

        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException("Missing required config key: " + key);
        }

        return value;
    }

}
