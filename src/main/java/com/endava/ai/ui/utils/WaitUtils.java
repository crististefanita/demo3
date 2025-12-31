package com.endava.ai.ui.utils;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.ui.core.DriverManager;

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
        StepLogger.logDetail("Wait for visible: " + cssSelector);
        DriverManager.getEngine().waitForVisible(cssSelector, EXPLICIT_WAIT_SECONDS);
    }

    public static void waitForUrlContains(String fragment) {
        StepLogger.logDetail("Wait for URL contains: " + fragment);
        try {
            DriverManager.getEngine().waitForUrlContains(fragment, EXPLICIT_WAIT_SECONDS);
        } catch (Exception e) {
            StepLogger.fail("Wait failed for URL contains: " + fragment, e);
            throw e;
        }
    }
}
