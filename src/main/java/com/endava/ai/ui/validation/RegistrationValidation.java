package com.endava.ai.ui.validation;

import com.endava.ai.ui.core.DriverManager;
import com.endava.ai.ui.pages.LoginPage;
import com.endava.ai.ui.pages.RegisterPage;
import com.endava.ai.ui.utils.UIActions;
import com.endava.ai.ui.utils.WaitUtils;
import org.testng.Assert;

import java.time.Duration;

/**
 * Validations → assertions only.
 */
public final class RegistrationValidation {
    public void assertRedirectedToLogin() {
        String outcome = WaitUtils.waitUntil(
                () -> {
                    String url = DriverManager.getEngine().getCurrentUrl();
                    String body = safeText("body");
                    boolean loginVisible = safeVisible(LoginPage.LOGIN_BUTTON);
                    boolean registerStillVisible = safeVisible(RegisterPage.FIRST_NAME);
                    String errorText = safeText(RegisterPage.ANY_ERROR);

                    if (loginVisible && !registerStillVisible
                            && (url.contains("/auth/login")
                            || body.contains("Sign in with Google")
                            || body.contains("Email address *"))) {
                        return "login-surface";
                    }

                    if (registerStillVisible
                            && url.contains("/auth/register")
                            && errorText != null
                            && !errorText.trim().isEmpty()) {
                        return "register-error";
                    }

                    return "pending";
                },
                value -> !"pending".equals(value),
                Duration.ofSeconds(20),
                Duration.ofMillis(250)
        );

        if (!"login-surface".equals(outcome)) {
            throw new AssertionError(
                    "Expected registration success to resolve to login surface, but browser ended on a register error surface. Current URL: "
                            + DriverManager.getEngine().getCurrentUrl()
            );
        }

        String url = DriverManager.getEngine().getCurrentUrl();
        Assert.assertTrue(
                url.contains("/auth/login") || DriverManager.getEngine().isVisible(LoginPage.LOGIN_BUTTON),
                "Expected redirect to login surface, but was: " + url
        );
    }

    public void assertStillOnRegister() {
        String url = DriverManager.getEngine().getCurrentUrl();
        Assert.assertTrue(url.contains("/auth/register"), "Expected to remain on /auth/register, but was: " + url);
    }

    public void assertAnyErrorVisibleOrPresent() {
        WaitUtils.waitForCondition(
                "registration error is visible",
                () -> {
                    String text = DriverManager.getEngine().getText(RegisterPage.ANY_ERROR);
                    return text != null && !text.trim().isEmpty();
                },
                "Expected some validation error to be present, but none found."
        );
        String text = UIActions.getText(RegisterPage.ANY_ERROR, "Any error message container");
        Assert.assertTrue(text != null && !text.trim().isEmpty(), "Expected some validation error to be present, but none found.");
    }

    public void assertErrorContains(String expectedText) {
        assertTextContains(RegisterPage.ANY_ERROR, "Any error message container", expectedText);
    }

    public void assertRegistrationFormContains(String expectedText) {
        assertErrorContains(expectedText);
    }

    public void assertPasswordErrorContains(String expectedText) {
        assertTextContains(RegisterPage.PASSWORD_ERROR, "Password validation message", expectedText);
    }

    public void assertPhoneErrorContains(String expectedText) {
        assertTextContains(RegisterPage.PHONE_ERROR, "Phone validation message", expectedText);
    }

    private void assertTextContains(String locator, String description, String expectedText) {
        WaitUtils.waitForCondition(
                description + " contains " + expectedText,
                () -> DriverManager.getEngine().getText(locator).contains(expectedText),
                "Expected " + description + " to contain '" + expectedText + "'"
        );
        String text = UIActions.getText(locator, description);
        Assert.assertTrue(
                text.contains(expectedText),
                "Expected " + description + " to contain '" + expectedText + "', but was: " + text
        );
    }

    private String safeText(String locator) {
        try {
            return DriverManager.getEngine().getText(locator);
        } catch (Exception ignored) {
            return "";
        }
    }

    private boolean safeVisible(String locator) {
        try {
            return DriverManager.getEngine().isVisible(locator);
        } catch (Exception ignored) {
            return false;
        }
    }
}
