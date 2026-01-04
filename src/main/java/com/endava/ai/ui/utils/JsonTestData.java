package com.endava.ai.ui.utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Tiny JSON reader for demo testdata (kept minimal to avoid extra deps).
 * This is NOT a general JSON parser; it extracts simple string values by key from a flat JSON object.
 */
public final class JsonTestData {
    private JsonTestData() {}

    public static String readResource(String resourcePath) {
        InputStream is = null;

        try {
            is = JsonTestData.class
                    .getClassLoader()
                    .getResourceAsStream(resourcePath);

            if (is == null) {
                Path path = Path.of("src/test/resources", resourcePath);
                if (!Files.exists(path)) {
                    path = Path.of("src/main/resources", resourcePath);
                }
                if (Files.exists(path)) {
                    is = Files.newInputStream(path);
                }
            }

            if (is == null) {
                throw new IllegalStateException("Missing resource: " + resourcePath);
            }

            return new String(is.readAllBytes(), StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed reading resource: " + resourcePath, e
            );
        } finally {
            try {
                if (is != null) is.close();
            } catch (Exception ignored) {
            }
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
