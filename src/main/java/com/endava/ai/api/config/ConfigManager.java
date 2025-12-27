package com.endava.ai.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Loads and exposes API framework configuration.
 * Ensures reports.dir exists before any report is produced.
 */
public final class ConfigManager {
    private static final String CONFIG_FILE = "framework.properties";
    private static final Properties PROPS = new Properties();
    private static volatile boolean loaded = false;

    private ConfigManager() {}

    public static void load() {
        if (loaded) return;
        synchronized (ConfigManager.class) {
            if (loaded) return;
            try (InputStream is = ConfigManager.class
                    .getClassLoader()
                    .getResourceAsStream(CONFIG_FILE)) {

                if (is == null) {
                    throw new IllegalStateException(
                            "Missing " + CONFIG_FILE + " under src/main/resources");
                }

                PROPS.load(is);

                // IMPORTANT: mark loaded before any get()/require()
                loaded = true;

                ensureReportsDirExists();
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load " + CONFIG_FILE, e);
            }
        }
    }

    private static void ensureReportsDirExists() {
        String dir = PROPS.getProperty("reports.dir");
        if (dir == null || dir.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Missing required config property: reports.dir");
        }
        Path p = Paths.get(dir.trim());
        try {
            Files.createDirectories(p);
        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to create reports.dir: " + p.toAbsolutePath(), e);
        }
    }

    public static String get(String key) {
        load();
        return PROPS.getProperty(key);
    }

    public static String require(String key) {
        String v = get(key);
        if (v == null || v.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Missing required config property: " + key);
        }
        return v.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(require(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(require(key));
    }

    public static boolean getBool(String key) {
        return getBoolean(key);
    }

    /* ================= API-specific helpers ================= */

    public static String getApiBaseUrl() {
        return require("base.url.api");
    }

    public static String getAuthToken() {
        return require("auth.token");
    }
}
