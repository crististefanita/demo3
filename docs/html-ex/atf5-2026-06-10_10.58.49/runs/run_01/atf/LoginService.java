package com.endava.ai.ui.service;

import com.endava.ai.ui.model.CustomerData;
import com.endava.ai.ui.pages.LoginPage;
import com.endava.ai.ui.utils.UIActions;
import com.endava.ai.ui.utils.WaitUtils;

public final class LoginService {

    public void openLogin() {
        UIActions.navigateToRelative("/auth/login");
        WaitUtils.waitForVisible(LoginPage.EMAIL);
    }

    public void login(CustomerData customer) {
        login(customer.getEmail(), customer.getPassword());
    }

    public void login(String email, String password) {
        UIActions.type(LoginPage.EMAIL, "Email address", email);
        UIActions.type(LoginPage.PASSWORD, "Password", password);
        UIActions.click(LoginPage.LOGIN_BUTTON, "Login");
    }
}
