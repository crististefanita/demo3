package com.endava.ai.api.utils;

import com.google.gson.Gson;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class JsonTestDataReader {
    private static final Gson GSON = new Gson();

    private JsonTestDataReader() {}

    public static <T> T read(String resourcePath, Class<T> clazz) {
        try (InputStream is = JsonTestDataReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Test data not found on classpath: " + resourcePath);
            }
            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return GSON.fromJson(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed reading test data: " + resourcePath, e);
        }
    }

    public static String readRaw(String resourcePath) {
        try (InputStream is = JsonTestDataReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Test data not found on classpath: " + resourcePath);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed reading test data: " + resourcePath, e);
        }
    }
}
