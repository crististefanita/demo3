package com.endava.ai.atf.ui;

import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.ui.core.BaseTestUI;
import com.endava.ai.ui.factory.UserDataFactory;
import com.endava.ai.ui.model.RegistrationData;
import com.endava.ai.ui.service.RegistrationService;
import com.endava.ai.ui.validation.RegistrationValidation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegistrationTests extends BaseTestUI {

    private final RegistrationService service = new RegistrationService();
    private final RegistrationValidation validation = new RegistrationValidation();

    @BeforeMethod
    public void before() {
        StepLogger.startStep("Registration preconditions");
        StepLogger.pass("Ready");
    }

    @Test(description = "Positive: successful registration redirects to login")
    public void positive_registration_redirects_to_login() {
        service.openRegister();
        RegistrationData data = UserDataFactory.validRegistrationData();
        service.register(data);

        validation.assertRedirectedToLogin();
    }

    @Test(description = "Negative: missing required fields should show validation error")
    public void negative_missing_required_fields_shows_error() {
        service.openRegister();

        com.endava.ai.ui.utils.UIActions.click(com.endava.ai.ui.pages.RegisterPage.REGISTER_BUTTON, "Register");

        validation.assertStillOnRegister();
        validation.assertAnyErrorVisibleOrPresent();
    }

    @Test(description = "Negative: invalid email format should not redirect to login")
    public void negative_invalid_email_should_fail() {
        service.openRegister();

        RegistrationData data = UserDataFactory.validRegistrationData()
                .withEmail("invalid-email-format");
        service.register(data);

        validation.assertStillOnRegister();
        validation.assertErrorContains("Email format is invalid");
    }

    @Test(description = "Negative: duplicate email should fail on second registration attempt")
    public void negative_duplicate_email_should_fail() {
        service.openRegister();

        RegistrationData data = UserDataFactory.validRegistrationData();
        service.register(data);
        validation.assertRedirectedToLogin();

        service.openRegister();
        service.register(data);
        validation.assertStillOnRegister();
        validation.assertErrorContains("A customer with this email address already exists.");
    }

    @Test(description = "Negative: weak password should show password policy feedback")
    public void negative_weak_password_should_show_validation_message() {
        service.openRegister();

        RegistrationData data = UserDataFactory.validRegistrationData()
                .withPassword("short");
        service.register(data);

        validation.assertStillOnRegister();
        validation.assertPasswordErrorContains("Password must be minimal 6 characters long.");
    }

    @Test(description = "Negative: phone with letters should fail validation")
    public void negative_phone_with_letters_should_fail() {
        service.openRegister();

        RegistrationData data = UserDataFactory.validRegistrationData()
                .withPhone("abc");
        service.register(data);

        validation.assertStillOnRegister();
        validation.assertPhoneErrorContains("Only numbers are allowed.");
    }

}
