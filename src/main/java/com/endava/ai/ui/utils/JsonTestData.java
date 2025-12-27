package com.endava.ai.ui.utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Tiny JSON reader for demo testdata (kept minimal to avoid extra deps).
 * This is NOT a general JSON parser; it extracts simple string values by key from a flat JSON object.
 */
public final class JsonTestData {
    private JsonTestData() {}

    public static String readResource(String path) {
        try (InputStream is = JsonTestData.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) throw new IllegalStateException("Missing resource: " + path);
            byte[] bytes = is.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Failed reading resource: " + path, e);
        }
    }

    public static String getString(String json, String key) {
        String search = "\"" + key + "\":";
        int i = json.indexOf(search);
        if (i < 0) return "";
        int start = json.indexOf("\"", i + search.length()) + 1;
        int end = json.indexOf("\"", start);
        if (start <= 0 || end <= start) return "";
        return json.substring(start, end);
    }
}
