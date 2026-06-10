package com.endava.ai.ui.validation;

import com.endava.ai.ui.core.DriverManager;
import com.endava.ai.ui.pages.AccountPage;
import com.endava.ai.ui.pages.LoginPage;
import com.endava.ai.ui.utils.UIActions;
import com.endava.ai.ui.utils.WaitUtils;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public final class LoginValidation {
    private static final int ACCOUNT_REDIRECT_TIMEOUT_SECONDS = 5;
    private static final int ACCOUNT_SURFACE_TIMEOUT_SECONDS = 15;

    public void assertAuthenticatedAccountSurface() {
        try {
            DriverManager.getEngine().waitForUrlContains("/account", ACCOUNT_REDIRECT_TIMEOUT_SECONDS);
            DriverManager.getEngine().waitForVisible(AccountPage.NAV_PROFILE, ACCOUNT_SURFACE_TIMEOUT_SECONDS);
        } catch (RuntimeException e) {
            throw buildAccountSurfaceAssertionError(e);
        }

        String url = DriverManager.getEngine().getCurrentUrl();
        boolean profileVisible = safeVisible(AccountPage.NAV_PROFILE);
        Assert.assertTrue(
                url.contains("/account") || profileVisible,
                buildAuthenticatedAccountFailureMessage()
        );
    }

    public void assertStillOnLogin() {
        String url = DriverManager.getEngine().getCurrentUrl();
        Assert.assertTrue(url.contains("/auth/login"), "Expected to remain on /auth/login, but was: " + url);
    }

    public void assertLoginErrorContains(String expectedText) {
        WaitUtils.waitForCondition(
                "login error contains " + expectedText,
                () -> {
                    String text = DriverManager.getEngine().getText(LoginPage.LOGIN_ERROR);
                    return text != null && text.contains(expectedText);
                },
                "Expected login error to contain '" + expectedText + "'"
        );

        String text = UIActions.getText(LoginPage.LOGIN_ERROR, "Login error message");
        Assert.assertTrue(
                text.contains(expectedText),
                "Expected login error to contain '" + expectedText + "', but was: " + text
        );
    }

    private AssertionError buildAccountSurfaceAssertionError(Throwable cause) {
        return new AssertionError(buildAuthenticatedAccountFailureMessage(), cause);
    }

    private String buildAuthenticatedAccountFailureMessage() {
        String url = DriverManager.getEngine().getCurrentUrl();
        List<String> evidence = new ArrayList<>();
        evidence.add("Current URL: " + url);
        evidence.add("login button visible: " + yesNo(safeVisible(LoginPage.LOGIN_BUTTON)));
        evidence.add("profile navigation visible: " + yesNo(safeVisible(AccountPage.NAV_PROFILE)));

        String loginError = safeText(LoginPage.LOGIN_ERROR);
        if (!loginError.isBlank()) {
            evidence.add("login error: " + loginError);
        }

        return "Expected authenticated account surface, but it did not materialize. "
                + String.join(" | ", evidence);
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

    private String yesNo(boolean value) {
        return value ? "yes" : "no";
    }
}
