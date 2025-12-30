package com.endava.ai.ui.utils;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.ui.core.DriverManager;

public final class WaitUtils {

    private static final int EXPLICIT_WAIT_SECONDS = ConfigManager.getInt("explicit.wait.seconds");

    private WaitUtils() {
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
