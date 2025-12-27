package com.endava.ai.ui.validation;

import com.endava.ai.ui.core.DriverManager;
import com.endava.ai.ui.pages.RegisterPage;
import com.endava.ai.ui.utils.UIActions;
import org.testng.Assert;

/**
 * Validations → assertions only.
 */
public final class RegistrationValidation {
    public void assertRedirectedToLogin() {
        String url = DriverManager.getEngine().getCurrentUrl();
        Assert.assertTrue(url.contains("/auth/login"), "Expected redirect to /auth/login, but was: " + url);
    }

    public void assertStillOnRegister() {
        String url = DriverManager.getEngine().getCurrentUrl();
        Assert.assertTrue(url.contains("/auth/register"), "Expected to remain on /auth/register, but was: " + url);
    }

    public void assertAnyErrorVisibleOrPresent() {
        String text = UIActions.getText(RegisterPage.ANY_ERROR, "Any error message container");
        Assert.assertTrue(text != null && !text.trim().isEmpty(), "Expected some validation error to be present, but none found.");
    }
}
