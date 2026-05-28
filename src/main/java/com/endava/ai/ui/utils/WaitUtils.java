package com.endava.ai.ui.utils;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.ui.core.DriverManager;

import java.time.Duration;
import java.time.Instant;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class WaitUtils {

    private static final String UI_WAIT_TIMEOUT_SECONDS_KEY = "ui.wait.timeout.seconds";
    private static final String LEGACY_WAIT_TIMEOUT_SECONDS_KEY = "explicit.wait.seconds";
    private static final int UI_WAIT_TIMEOUT_SECONDS = getExplicitWait();

    private WaitUtils() {
    }

    private static int getExplicitWait() {
        String key = UI_WAIT_TIMEOUT_SECONDS_KEY;
        String value = ConfigManager.get(
                UI_WAIT_TIMEOUT_SECONDS_KEY,
                ConfigManager.get(LEGACY_WAIT_TIMEOUT_SECONDS_KEY, "10")
        );
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Missing or invalid integer config key: " + key, e
            );
        }
    }

    public static void waitForVisible(String cssSelector) {
        logDetailIfPossible("Wait for visible: " + cssSelector);
        DriverManager.getEngine().waitForVisible(cssSelector, UI_WAIT_TIMEOUT_SECONDS);
    }

    public static void waitForUrlContains(String fragment) {
        logDetailIfPossible("Wait for URL contains: " + fragment);
        DriverManager.getEngine().waitForUrlContains(fragment, UI_WAIT_TIMEOUT_SECONDS);
    }

    public static void waitForCondition(String description,
                                        BooleanSupplier condition,
                                        String failureMessage) {
        logDetailIfPossible("Wait for condition: " + description);

        Boolean matched = waitUntil(
                condition::getAsBoolean,
                Boolean.TRUE::equals,
                Duration.ofMillis(250)
        );

        if (!Boolean.TRUE.equals(matched)) {
            throw new AssertionError(failureMessage);
        }
    }

    public static void waitForInputValue(String cssSelector,
                                         String description,
                                         Predicate<String> condition) {
        logDetailIfPossible("Wait for input value: " + description);

        String value = waitUntil(
                () -> DriverManager.getEngine().getValue(cssSelector),
                current -> current != null && condition.test(current),
                Duration.ofMillis(250)
        );

        if (value == null || !condition.test(value)) {
            throw new AssertionError("Timed out waiting for input value: " + description);
        }
    }

    public static <T> T waitUntil(Supplier<T> supplier,
                                  Predicate<T> condition,
                                  Duration pollInterval) {
        Instant end = Instant.now().plusSeconds(UI_WAIT_TIMEOUT_SECONDS);
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
