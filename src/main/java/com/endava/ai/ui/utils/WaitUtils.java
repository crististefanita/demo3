package com.endava.ai.ui.utils;

import com.endava.ai.ui.config.ConfigManager;
import com.endava.ai.ui.core.DriverManager;
import com.endava.ai.core.reporting.StepLogger;

/**
 * ONLY place allowed to execute waits and log wait failures.
 */
public final class WaitUtils {
    private WaitUtils() {}

    public static void waitForVisible(String cssSelector) {
        int seconds = ConfigManager.getInt("explicit.wait.seconds");
        StepLogger.logDetail("Wait for visible: " + cssSelector + " (timeout=" + seconds + "s)");
        DriverManager.getEngine().waitForVisible(cssSelector, seconds);
    }

    public static void waitForUrlContains(String fragment) {
        int seconds = ConfigManager.getInt("explicit.wait.seconds");
        StepLogger.logDetail("Wait for URL contains: " + fragment + " (timeout=" + seconds + "s)");
        try {
            DriverManager.getEngine().waitForUrlContains(fragment, seconds);
        } catch (Exception e) {
            StepLogger.fail("Wait failed for URL contains: " + fragment, e);
            throw e;
        }
    }
}
