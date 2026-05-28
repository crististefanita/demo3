package com.endava.ai.api.utils;

import com.endava.ai.core.config.ConfigManager;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

/**
 * API module has no UI waits; provided to keep a consistent contract with cross-cutting requirements.
 */
public final class WaitUtils {

    private static final String API_WAIT_TIMEOUT_SECONDS_KEY = "api.wait.timeout.seconds";
    private static final String LEGACY_WAIT_TIMEOUT_SECONDS_KEY = "explicit.wait.seconds";
    private static final int DEFAULT_WAIT_SECONDS = getExplicitWait();

    private WaitUtils() {
    }

    private static int getExplicitWait() {
        String key = API_WAIT_TIMEOUT_SECONDS_KEY;
        String value = ConfigManager.get(
                API_WAIT_TIMEOUT_SECONDS_KEY,
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

    public static <T> T waitUntil(Supplier<T> supplier,
                                  java.util.function.Predicate<T> condition,
                                  Duration pollInterval) {

        Instant end = Instant.now().plusSeconds(DEFAULT_WAIT_SECONDS);
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

    private static void sleep(Duration d) {
        try {
            Thread.sleep(d.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
