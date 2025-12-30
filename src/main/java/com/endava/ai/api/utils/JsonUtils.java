package com.endava.ai.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class JsonUtils {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private JsonUtils() {}

    public static String toJson(Object o) {
        if (o == null) return "";
        return GSON.toJson(o);
    }

    public static String readString(String resourcePath, String key) {
        JsonObject json = readJson(resourcePath);
        return json.has(key) && !json.get(key).isJsonNull()
                ? json.get(key).getAsString()
                : "";
    }

    private static JsonObject readJson(String resourcePath) {
        try (InputStream is = JsonUtils.class
                .getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (is == null) {
                throw new IllegalStateException("Missing resource: " + resourcePath);
            }

            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return GSON.fromJson(content, JsonObject.class);

        } catch (Exception e) {
            throw new IllegalStateException("Failed reading resource: " + resourcePath, e);
        }
    }
}
