package com.endava.ai.ui.utils;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.ui.core.DriverManager;

public final class UIActions {

    private UIActions() {
    }

    public static void navigateToRelative(String relativePath) {
        String baseUrl = ConfigManager.require("base.url");
        String fullUrl = baseUrl + relativePath;

        execute(
                "Navigate to URL: " + fullUrl,
                () -> {
                    StepLogger.logDetail("base.url=" + baseUrl);
                    StepLogger.logDetail("relativePath=" + relativePath);
                    DriverManager.getEngine().open(fullUrl);
                    WaitUtils.waitForUrlContains(relativePath);
                },
                "Navigated",
                "Failed navigating to: " + fullUrl
        );
    }

    public static void click(String cssSelector, String description) {
        execute(
                "Click on: " + description,
                () -> {
                    StepLogger.logDetail("locator=" + cssSelector);
                    waitIfRequired(cssSelector);
                    DriverManager.getEngine().click(cssSelector);
                },
                "Clicked",
                "Failed clicking: " + description
        );
    }

    public static void type(String cssSelector, String description, String value) {
        execute(
                "Type into element: " + description,
                () -> {
                    StepLogger.logDetail("locator=" + cssSelector);
                    StepLogger.logDetail("value=" + value);
                    waitIfRequired(cssSelector);
                    DriverManager.getEngine().type(cssSelector, value);
                },
                "Typed",
                "Failed typing into: " + description
        );
    }

    @SuppressWarnings("unused")
    public static void select(String cssSelector, String description, String value) {
        execute(
                "Select option: " + description,
                () -> {
                    StepLogger.logDetail("locator=" + cssSelector);
                    StepLogger.logDetail("value=" + value);

                    if (!DriverManager.getEngine().supportsAutoWait()) {
                        WaitUtils.waitForVisible(cssSelector);
                    }

                    DriverManager.getEngine().click(cssSelector);
                    DriverManager.getEngine().click(cssSelector + " option[value='" + value + "']");
                },
                "Selected",
                "Failed selecting: " + description
        );
    }

    public static String getText(String cssSelector, String description) {
        final String[] result = new String[1];

        execute(
                "Get text from: " + description,
                () -> {
                    StepLogger.logDetail("locator=" + cssSelector);
                    waitIfRequired(cssSelector);
                    result[0] = DriverManager.getEngine().getText(cssSelector);
                    StepLogger.logDetail("text=" + result[0]);
                },
                "Text captured",
                "Failed getting text from: " + description
        );

        return result[0];
    }

    private static void execute(String stepTitle, Runnable action, String successMessage, String failureMessage) {
        StepLogger.startStep(stepTitle);
        try {
            action.run();
            StepLogger.pass(successMessage);
        } catch (Throwable e) {
            StepLogger.fail(failureMessage, e);
            throw new AssertionError("UI Action failed: " + stepTitle);
        }
    }

    private static void waitIfRequired(String cssSelector) {
        if (!DriverManager.getEngine().supportsAutoWait()) {
            WaitUtils.waitForVisible(cssSelector);
        }
    }
}
