package com.endava.ai.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class JsonUtils {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private JsonUtils() {}

    public static String toJson(Object o) {
        if (o == null) return "";
        return GSON.toJson(o);
    }
}
