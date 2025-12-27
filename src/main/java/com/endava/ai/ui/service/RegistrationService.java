package com.endava.ai.ui.service;

import com.endava.ai.ui.pages.RegisterPage;
import com.endava.ai.ui.utils.UIActions;

/**
 * Services → workflows only. Must remain UI-engine agnostic.
 * Must handle optional/conditionally rendered UI (best-effort for demo).
 */
public final class RegistrationService {
    public void openRegister() {
        UIActions.navigateToRelative("/auth/register");
    }

    public void register(String firstName, String lastName, String dob, String street,
                         String postal, String city, String state, String country,
                         String phone, String email, String password) {

        UIActions.type(RegisterPage.FIRST_NAME, "First name", firstName);
        UIActions.type(RegisterPage.LAST_NAME, "Last name", lastName);

        // DOB is mandatory on the demo page; still handle as "optional" if selector isn't present in a future UI variation
        try {
            UIActions.type(RegisterPage.DOB, "Date of Birth", dob);
        } catch (Exception ignored) {}

        UIActions.type(RegisterPage.STREET, "Street", street);
        UIActions.type(RegisterPage.POSTAL, "Postal code", postal);
        UIActions.type(RegisterPage.CITY, "City", city);
        UIActions.type(RegisterPage.STATE, "State", state);
        UIActions.type(RegisterPage.COUNTRY, "Country", country);
        UIActions.type(RegisterPage.PHONE, "Phone", phone);

        UIActions.type(RegisterPage.EMAIL, "Email address", email);
        UIActions.type(RegisterPage.PASSWORD, "Password", password);

        UIActions.click(RegisterPage.REGISTER_BUTTON, "Register");
    }
}
