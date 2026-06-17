package com.endava.ai.atf.ui;

import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.ui.core.BaseTestUI;
import com.endava.ai.ui.factory.UserDataFactory;
import com.endava.ai.ui.model.RegistrationData;
import com.endava.ai.ui.service.AccountService;
import com.endava.ai.ui.service.LoginService;
import com.endava.ai.ui.service.ProfileService;
import com.endava.ai.ui.service.RegistrationService;
import com.endava.ai.ui.validation.ProfileValidation;
import com.endava.ai.ui.validation.RegistrationValidation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AuthenticatedProfileTests extends BaseTestUI {

    private final RegistrationService registrationService = new RegistrationService();
    private final RegistrationValidation registrationValidation = new RegistrationValidation();
    private final LoginService loginService = new LoginService();
    private final AccountService accountService = new AccountService();
    private final ProfileService profileService = new ProfileService();
    private final ProfileValidation profileValidation = new ProfileValidation();

    @BeforeMethod
    public void before() {
        StepLogger.startStep("Authenticated profile preconditions");
        StepLogger.pass("Ready");
    }

    @Test(description = "Positive: registered user can open the authenticated profile form with prefilled registration data")
    public void positive_registered_user_can_open_profile_form_with_prefilled_registration_data() {
        RegistrationData data = UserDataFactory.validRegistrationData();

        registrationService.openRegister();
        registrationService.register(data);
        registrationValidation.assertRedirectedToLogin();

        loginService.login(data.email(), data.password());
        accountService.openAccountOverview();
        profileService.openProfileForm();
        profileValidation.assertProfileFormPrefilled(data);
    }
}
