package com.endava.ai.ui.service;

import com.endava.ai.ui.factory.UserDataFactory;
import com.endava.ai.ui.model.CustomerData;
import com.endava.ai.ui.model.RegistrationData;
import com.endava.ai.ui.pages.RegisterPage;
import com.endava.ai.ui.utils.UIActions;
import com.endava.ai.ui.utils.WaitUtils;

/**
 * Services → workflows only. Must remain UI-engine agnostic.
 * Must handle optional/conditionally rendered UI (best-effort for demo).
 */
public final class RegistrationService {
    public void openRegister() {
        UIActions.navigateToRelative("/auth/register");
        WaitUtils.waitForVisible(RegisterPage.FIRST_NAME);
    }

    public RegistrationData registerValidUser() {
        RegistrationData data = UserDataFactory.validRegistrationData();
        register(data);
        return data;
    }

    public CustomerData registerValidCustomer() {
        CustomerData data = com.endava.ai.ui.factory.CustomerDataFactory.validCustomer();
        register(data);
        return data;
    }

    public void register(RegistrationData data) {
        register(
                data.firstName(),
                data.lastName(),
                data.dob(),
                data.street(),
                data.postal(),
                data.houseNumber(),
                data.city(),
                data.state(),
                data.country(),
                data.phone(),
                data.email(),
                data.password()
        );
    }

    public void register(CustomerData data) {
        register(
                data.getFirstName(),
                data.getLastName(),
                data.getDob(),
                data.getStreet(),
                data.getPostalCode(),
                data.getHouseNumber(),
                data.getCity(),
                data.getState(),
                data.getCountry(),
                data.getPhone(),
                data.getEmail(),
                data.getPassword()
        );
    }

    public void register(String firstName, String lastName, String dob, String street,
                         String postal, String houseNumber, String city, String state, String country,
                         String phone, String email, String password) {

        UIActions.type(RegisterPage.FIRST_NAME, "First name", firstName);
        UIActions.type(RegisterPage.LAST_NAME, "Last name", lastName);

        // DOB is mandatory on the demo page; still handle as "optional" if selector isn't present in a future UI variation
        try {
            UIActions.type(RegisterPage.DOB, "Date of Birth", dob);
        } catch (Exception ignored) {}

        UIActions.type(RegisterPage.STREET, "Street", street);
        UIActions.type(RegisterPage.POSTAL, "Postal code", postal);
        UIActions.type(RegisterPage.HOUSE_NUMBER, "House number", houseNumber);
        UIActions.type(RegisterPage.CITY, "City", city);
        UIActions.type(RegisterPage.STATE, "State", state);
        UIActions.select(RegisterPage.COUNTRY, "Country", country);
        UIActions.type(RegisterPage.PHONE, "Phone", phone);

        UIActions.type(RegisterPage.EMAIL, "Email address", email);
        UIActions.type(RegisterPage.PASSWORD, "Password", password);

        UIActions.click(RegisterPage.REGISTER_BUTTON, "Register");
    }

    public void submitEmptyForm() {
        UIActions.click(RegisterPage.REGISTER_BUTTON, "Register");
    }

    public void submitRegistration() {
        UIActions.click(RegisterPage.REGISTER_BUTTON, "Register");
    }
}
