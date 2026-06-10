package com.endava.ai.ui.validation;

import com.endava.ai.ui.core.DriverManager;
import com.endava.ai.ui.pages.LoginPage;
import com.endava.ai.ui.pages.RegisterPage;
import com.endava.ai.ui.utils.UIActions;
import com.endava.ai.ui.utils.WaitUtils;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Validations → assertions only.
 */
public final class RegistrationValidation {
    private static final int LOGIN_REDIRECT_TIMEOUT_SECONDS = 5;
    private static final int LOGIN_SURFACE_TIMEOUT_SECONDS = 15;

    public void assertRedirectedToLogin() {
        try {
            DriverManager.getEngine().waitForUrlContains("/auth/login", LOGIN_REDIRECT_TIMEOUT_SECONDS);
            DriverManager.getEngine().waitForVisible(LoginPage.LOGIN_BUTTON, LOGIN_SURFACE_TIMEOUT_SECONDS);
        } catch (RuntimeException e) {
            throw buildLoginSurfaceAssertionError(e);
        }

        String url = DriverManager.getEngine().getCurrentUrl();
        boolean loginVisible = safeVisible(LoginPage.LOGIN_BUTTON);
        Assert.assertTrue(
                url.contains("/auth/login") || loginVisible,
                buildRegistrationSuccessFailureMessage()
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

    private AssertionError buildLoginSurfaceAssertionError(Throwable cause) {
        return new AssertionError(buildRegistrationSuccessFailureMessage(), cause);
    }

    private String buildRegistrationSuccessFailureMessage() {
        String url = DriverManager.getEngine().getCurrentUrl();
        List<String> evidence = new ArrayList<>();
        evidence.add("Current URL: " + url);
        evidence.add("login button visible: " + yesNo(safeVisible(LoginPage.LOGIN_BUTTON)));
        evidence.add("register form visible: " + yesNo(safeVisible(RegisterPage.FIRST_NAME)));

        String registerError = safeText(RegisterPage.ANY_ERROR);
        if (!registerError.isBlank()) {
            evidence.add("register error: " + registerError);
        }

        return "Expected registration success to resolve to login surface, but it did not. "
                + String.join(" | ", evidence);
    }

    private String yesNo(boolean value) {
        return value ? "yes" : "no";
    }
}
