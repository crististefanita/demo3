package com.endava.ai.core.config;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ConfigManager {

    private static volatile Properties PROPERTIES;

    private ConfigManager() {
    }

    public static String get(String key, String defaultValue) {
        String value = properties().getProperty(key);
        if (value == null) return defaultValue == null ? null : defaultValue.trim();
        value = value.trim();
        return value.isEmpty() ? (defaultValue == null ? null : defaultValue.trim()) : value;
    }

    public static String require(String key) {
        String value = properties().getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Missing config key: " + key);
        }
        value = value.trim();
        if (value.isEmpty()) {
            throw new IllegalStateException("Config key is empty: " + key);
        }
        return value;
    }

    public static void set(String key, String value) {
        if (value == null) {
            properties().remove(key);
        } else {
            properties().setProperty(key, value.trim());
        }
    }

    public static void clear(String key) {
        properties().remove(key);
    }

    private static Properties properties() {
        if (PROPERTIES == null) {
            synchronized (ConfigManager.class) {
                if (PROPERTIES == null) {
                    PROPERTIES = load();
                }
            }
        }
        return PROPERTIES;
    }

    private static Properties load() {
        Properties props = new Properties();
        InputStream is = null;

        try {
            is = ConfigManager.class
                    .getClassLoader()
                    .getResourceAsStream("framework.properties");

            if (is == null) {
                Path path = Path.of("src/main/resources/framework.properties");
                if (Files.exists(path)) {
                    is = Files.newInputStream(path);
                }
            }

            if (is == null) {
                throw new IllegalStateException(
                        "framework.properties not found. Checked classpath and src/main/resources"
                );
            }

            props.load(is);
            return props;

        } catch (Exception e) {
            throw new IllegalStateException("Failed to load framework.properties", e);
        } finally {
            try {
                if (is != null) is.close();
            } catch (Exception ignored) {
            }
        }
    }
}
