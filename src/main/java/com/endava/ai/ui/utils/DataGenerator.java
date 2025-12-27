package com.endava.ai.ui.utils;

import org.apache.commons.lang3.RandomStringUtils;

public final class DataGenerator {
    private DataGenerator() {}

    public static String uniqueEmail() {
        String token = RandomStringUtils.randomAlphanumeric(10).toLowerCase();
        return "user_" + token + "@example.com";
    }

    public static String strongPassword() {
        // meets: 8+, upper+lower, number, special
        return "Aa1@" + RandomStringUtils.randomAlphanumeric(8);
    }
}
