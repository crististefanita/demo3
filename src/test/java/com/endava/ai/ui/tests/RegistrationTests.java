package com.endava.ai.ui.tests;

import com.endava.ai.ui.core.BaseTestUI;
import com.endava.ai.ui.service.RegistrationService;
import com.endava.ai.ui.utils.DataGenerator;
import com.endava.ai.ui.utils.JsonTestData;
import com.endava.ai.ui.validation.RegistrationValidation;
import org.testng.annotations.Test;

public class RegistrationTests extends BaseTestUI {

    private final RegistrationService service = new RegistrationService();
    private final RegistrationValidation validation = new RegistrationValidation();

    @Test(description = "Positive: successful registration redirects to login")
    public void positive_registration_redirects_to_login() {
        service.openRegister();

        String json = JsonTestData.readResource("testdata/registration_positive.json");

        String email = DataGenerator.uniqueEmail();
        String password = DataGenerator.strongPassword();

        service.register(
                JsonTestData.getString(json, "firstName"),
                JsonTestData.getString(json, "lastName"),
                JsonTestData.getString(json, "dob"),
                JsonTestData.getString(json, "street"),
                JsonTestData.getString(json, "postal"),
                JsonTestData.getString(json, "city"),
                JsonTestData.getString(json, "state"),
                JsonTestData.getString(json, "country"),
                JsonTestData.getString(json, "phone"),
                email,
                password
        );

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

        String json = JsonTestData.readResource("testdata/registration_positive.json");
        String password = DataGenerator.strongPassword();

        service.register(
                JsonTestData.getString(json, "firstName"),
                JsonTestData.getString(json, "lastName"),
                JsonTestData.getString(json, "dob"),
                JsonTestData.getString(json, "street"),
                JsonTestData.getString(json, "postal"),
                JsonTestData.getString(json, "city"),
                JsonTestData.getString(json, "state"),
                JsonTestData.getString(json, "country"),
                JsonTestData.getString(json, "phone"),
                "invalid-email-format",
                password
        );

        // Stay on register page and show an error (best-effort; demo site behavior may vary)
        validation.assertStillOnRegister();
        validation.assertAnyErrorVisibleOrPresent();
    }

    @Test(description = "Negative: duplicate email should fail on second registration attempt")
    public void negative_duplicate_email_should_fail() {
        service.openRegister();

        String json = JsonTestData.readResource("testdata/registration_positive.json");
        String email = DataGenerator.uniqueEmail();
        String password = DataGenerator.strongPassword();

        // First attempt
        service.register(
                JsonTestData.getString(json, "firstName"),
                JsonTestData.getString(json, "lastName"),
                JsonTestData.getString(json, "dob"),
                JsonTestData.getString(json, "street"),
                JsonTestData.getString(json, "postal"),
                JsonTestData.getString(json, "city"),
                JsonTestData.getString(json, "state"),
                JsonTestData.getString(json, "country"),
                JsonTestData.getString(json, "phone"),
                email,
                password
        );
        validation.assertRedirectedToLogin();

        // Second attempt with same email (expect failure / stay on register)
        service.openRegister();
        service.register(
                JsonTestData.getString(json, "firstName"),
                JsonTestData.getString(json, "lastName"),
                JsonTestData.getString(json, "dob"),
                JsonTestData.getString(json, "street"),
                JsonTestData.getString(json, "postal"),
                JsonTestData.getString(json, "city"),
                JsonTestData.getString(json, "state"),
                JsonTestData.getString(json, "country"),
                JsonTestData.getString(json, "phone"),
                email,
                password
        );
        validation.assertStillOnRegister();
        validation.assertAnyErrorVisibleOrPresent();
    }
}
