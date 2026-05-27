package com.endava.ai.ui.utils;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.ui.core.DriverManager;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class WaitUtils {

    private static final int EXPLICIT_WAIT_SECONDS = getExplicitWait();

    private WaitUtils() {
    }

    private static int getExplicitWait() {
        String key = "explicit.wait.seconds";
        try {
            return Integer.parseInt(ConfigManager.get(key,"10"));
        } catch (Exception e) {
            throw new RuntimeException(
                    "Missing or invalid integer config key: " + key, e
            );
        }
    }

    public static void waitForVisible(String cssSelector) {
        logDetailIfPossible("Wait for visible: " + cssSelector);
        DriverManager.getEngine().waitForVisible(cssSelector, EXPLICIT_WAIT_SECONDS);
    }

    public static void waitForUrlContains(String fragment) {
        logDetailIfPossible("Wait for URL contains: " + fragment);
        DriverManager.getEngine().waitForUrlContains(fragment, EXPLICIT_WAIT_SECONDS);
    }

    public static <T> T waitUntil(Supplier<T> supplier,
                                  Predicate<T> condition,
                                  Duration pollInterval) {
        Instant end = Instant.now().plusSeconds(EXPLICIT_WAIT_SECONDS);
        T value;

        do {
            value = supplier.get();
            if (condition.test(value)) {
                return value;
            }
            sleep(pollInterval);
        } while (Instant.now().isBefore(end));

        return value;
    }

    private static void logDetailIfPossible(String detail) {
        try {
            StepLogger.logDetail(detail);
        } catch (IllegalStateException ignored) {
        }
    }

    private static void sleep(Duration d) {
        try {
            Thread.sleep(d.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
