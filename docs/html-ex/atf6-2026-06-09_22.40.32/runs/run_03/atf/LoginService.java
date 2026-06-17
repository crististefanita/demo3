package com.endava.ai.ui.service;

import com.endava.ai.ui.core.DriverManager;
import com.endava.ai.ui.pages.LoginPage;
import com.endava.ai.ui.utils.UIActions;
import com.endava.ai.ui.utils.WaitUtils;

public final class LoginService {

    public void openLogin() {
        UIActions.navigateToRelative("/auth/login");
        WaitUtils.waitForVisible(LoginPage.EMAIL);
    }

    public void login(String email, String password) {
        UIActions.type(LoginPage.EMAIL, "Email address", email);
        UIActions.type(LoginPage.PASSWORD, "Password", password);
        UIActions.click(LoginPage.LOGIN_BUTTON, "Login");

        WaitUtils.waitForCondition(
                "authenticated session is established",
                () -> {
                    String url = DriverManager.getEngine().getCurrentUrl();
                    String body = safeText("body");
                    return url.contains("/account")
                            || body.contains("My account")
                            || body.contains("Sign out");
                },
                "Expected login to establish an authenticated session before continuing."
        );
    }

    private String safeText(String selector) {
        try {
            return DriverManager.getEngine().getText(selector);
        } catch (Exception ignored) {
            return "";
        }
    }
}
