package com.endava.ai.api.utils;

import java.util.Random;

public final class DataGenerator {
    private DataGenerator() {}

    public static Random forTest(String testName) {
        long seed = (testName == null ? 0 : testName.hashCode());
        return new Random(seed);
    }

    public static String randomEmail(Random r) {
        int n = 100000 + r.nextInt(900000);
        return "user" + n + "@example.com";
    }

    public static String randomName(Random r) {
        int n = 1000 + r.nextInt(9000);
        return "John Doe " + n;
    }
}
