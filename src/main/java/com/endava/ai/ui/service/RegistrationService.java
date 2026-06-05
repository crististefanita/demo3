package com.endava.ai.ui.service;

import com.endava.ai.ui.factory.UserDataFactory;
import com.endava.ai.ui.core.DriverManager;
import com.endava.ai.ui.model.CustomerData;
import com.endava.ai.ui.model.RegistrationData;
import com.endava.ai.ui.pages.RegisterPage;
import com.endava.ai.ui.utils.UIActions;
import com.endava.ai.ui.utils.WaitUtils;

import java.time.Duration;

/**
 * Services → workflows only. Must remain UI-engine agnostic.
 * Must handle optional/conditionally rendered UI (best-effort for demo).
 */
public final class RegistrationService {
    public void openRegister() {
        try {
            navigateAndWaitForRegisterPage(10);
        } catch (RuntimeException | AssertionError firstFailure) {
            navigateAndWaitForRegisterPage(15);
        }
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
        fillRegistrationForm(
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
        submitRegistration();
    }

    public void register(CustomerData data) {
        fillRegistrationForm(
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
        submitRegistration();
    }

    public void fillRegistrationForm(RegistrationData data) {
        fillRegistrationForm(
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

    public void fillRegistrationForm(CustomerData data) {
        fillRegistrationForm(
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

    public void fillRegistrationForm(String firstName, String lastName, String dob, String street,
                                     String postal, String houseNumber, String city, String state, String country,
                                     String phone, String email, String password) {
        UIActions.type(RegisterPage.FIRST_NAME, "First name", firstName);
        UIActions.type(RegisterPage.LAST_NAME, "Last name", lastName);

        UIActions.type(RegisterPage.DOB, "Date of Birth", dob);
        UIActions.select(RegisterPage.COUNTRY, "Country", country);
        UIActions.type(RegisterPage.POSTAL, "Postal code", postal);
        UIActions.type(RegisterPage.HOUSE_NUMBER, "House number", houseNumber);
        waitForAutofillIfPresent();
        fillIfBlank(RegisterPage.STREET, "Street", street);
        fillIfBlank(RegisterPage.CITY, "City", city);
        fillIfBlank(RegisterPage.STATE, "State", state);
        UIActions.type(RegisterPage.PHONE, "Phone", phone);
        UIActions.type(RegisterPage.EMAIL, "Email address", email);
        UIActions.type(RegisterPage.PASSWORD, "Password", password);
    }

    public void submitEmptyForm() {
        UIActions.click(RegisterPage.REGISTER_BUTTON, "Register");
    }

    public void submitRegistration() {
        UIActions.click(RegisterPage.REGISTER_BUTTON, "Register");
    }

    private void waitForAutofillIfPresent() {
        try {
            WaitUtils.waitForInputValue(
                    RegisterPage.STREET,
                    "Street autofill",
                    value -> value != null && !value.isBlank(),
                    Duration.ofSeconds(2)
            );
        } catch (AssertionError ignored) {
        }
    }

    private void fillIfBlank(String locator, String description, String value) {
        String currentValue = DriverManager.getEngine().getValue(locator);
        if (currentValue == null || currentValue.isBlank()) {
            UIActions.type(locator, description, value);
        }
    }

    private void navigateAndWaitForRegisterPage(int visibleTimeoutSeconds) {
        UIActions.navigateToRelative("/auth/register");
        DriverManager.getEngine().waitForVisible(RegisterPage.FIRST_NAME, visibleTimeoutSeconds);
    }
}
