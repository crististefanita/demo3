package com.endava.ai.ui.utils;

import com.endava.ai.ui.config.ConfigManager;
import com.endava.ai.ui.core.DriverManager;
import com.endava.ai.core.reporting.StepLogger;

/**
 * Centralized UI interaction abstraction.
 * All UI interactions MUST be executed via UIActions.
 */
public final class UIActions {

    private UIActions() {}

    /* =========================
       Navigation
       ========================= */

    public static void navigateToRelative(String relativePath) {
        String baseUrl = ConfigManager.require("base.url");
        String fullUrl = baseUrl + relativePath;

        StepLogger.startStep("Navigate to URL: " + fullUrl);
        try {
            StepLogger.logDetail("base.url=" + baseUrl);
            StepLogger.logDetail("relativePath=" + relativePath);

            DriverManager.getEngine().open(fullUrl);
            StepLogger.pass("Navigated");
        } catch (Exception e) {
            StepLogger.fail("Failed navigating to: " + fullUrl, e);
            throw e;
        }
    }

    /* =========================
       Interactions
       ========================= */

    public static void click(String cssSelector, String description) {
        StepLogger.startStep("Click on: " + description);
        try {
            StepLogger.logDetail("locator=" + cssSelector);

            waitIfRequired(cssSelector);

            DriverManager.getEngine().click(cssSelector);
            StepLogger.pass("Clicked");
        } catch (Exception e) {
            StepLogger.fail("Failed clicking: " + description, e);
            throw e;
        }
    }

    public static void type(String cssSelector, String description, String value) {
        StepLogger.startStep("Type into element: " + description);
        try {
            StepLogger.logDetail("locator=" + cssSelector);
            StepLogger.logDetail("value=" + value);

            waitIfRequired(cssSelector);

            DriverManager.getEngine().type(cssSelector, value);
            StepLogger.pass("Typed");
        } catch (Exception e) {
            StepLogger.fail("Failed typing into: " + description, e);
            throw e;
        }
    }

    public static String getText(String cssSelector, String description) {
        StepLogger.startStep("Get text from: " + description);
        try {
            StepLogger.logDetail("locator=" + cssSelector);

            waitIfRequired(cssSelector);

            String text = DriverManager.getEngine().getText(cssSelector);
            StepLogger.logDetail("text=" + text);

            StepLogger.pass("Text captured");
            return text;
        } catch (Exception e) {
            StepLogger.fail("Failed getting text from: " + description, e);
            throw e;
        }
    }

    /* =========================
       Internal helpers
       ========================= */

    private static void waitIfRequired(String cssSelector) {
        if (!DriverManager.getEngine().supportsAutoWait()) {
            WaitUtils.waitForVisible(cssSelector);
        }
    }
}
