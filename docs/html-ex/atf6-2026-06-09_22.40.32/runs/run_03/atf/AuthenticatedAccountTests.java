package com.endava.ai.atf.ui;

import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.ui.core.BaseTestUI;
import com.endava.ai.ui.factory.UserDataFactory;
import com.endava.ai.ui.model.RegistrationData;
import com.endava.ai.ui.service.AccountService;
import com.endava.ai.ui.service.LoginService;
import com.endava.ai.ui.service.RegistrationService;
import com.endava.ai.ui.validation.AccountValidation;
import com.endava.ai.ui.validation.RegistrationValidation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AuthenticatedAccountTests extends BaseTestUI {

    private final RegistrationService registrationService = new RegistrationService();
    private final LoginService loginService = new LoginService();
    private final AccountService accountService = new AccountService();
    private final RegistrationValidation registrationValidation = new RegistrationValidation();
    private final AccountValidation accountValidation = new AccountValidation();

    @BeforeMethod
    public void before() {
        StepLogger.startStep("Authenticated account preconditions");
        StepLogger.pass("Ready");
    }

    @Test(description = "Positive: registered user can open authenticated account overview with persisted identity")
    public void positive_registered_user_can_open_authenticated_account_overview_with_persisted_identity() {
        RegistrationData data = UserDataFactory.validRegistrationData();

        registrationService.openRegister();
        registrationService.register(data);
        registrationValidation.assertRedirectedToLogin();

        loginService.login(data.email(), data.password());
        accountService.openAccountOverview();
        accountValidation.assertAccountOverviewIdentity(data);
    }
}
