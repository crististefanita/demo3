package com.endava.ai.api.utils;

import com.endava.ai.api.config.ConfigManager;
import com.endava.ai.core.reporting.StepLogger;

/**
 * API module has no UI waits; provided to keep a consistent contract with cross-cutting requirements.
 */
public final class WaitUtils {
    private WaitUtils() {}

    public static void waitSeconds(int seconds) {
        StepLogger.logDetail("Wait " + seconds + "s");
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void waitDefault() {
        int s = ConfigManager.getInt("explicit.wait.seconds");
        if (s > 0) waitSeconds(s);
    }
}
