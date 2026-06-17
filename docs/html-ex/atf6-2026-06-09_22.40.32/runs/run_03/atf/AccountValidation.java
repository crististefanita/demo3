package com.endava.ai.ui.validation;

import com.endava.ai.ui.model.RegistrationData;
import com.endava.ai.ui.utils.UIActions;
import com.endava.ai.ui.utils.WaitUtils;
import org.testng.Assert;

public final class AccountValidation {

    public void assertAccountOverviewIdentity(RegistrationData expected) {
        String fullName = expected.firstName() + " " + expected.lastName();

        WaitUtils.waitForCondition(
                "account overview contains registered identity",
                () -> safeText("body").contains(fullName),
                "Expected authenticated account overview to contain registered identity: " + fullName
        );

        String bodyText = safeText("body");
        Assert.assertTrue(
                bodyText.contains("My account"),
                "Expected account overview body to expose the authenticated account surface."
        );
        Assert.assertTrue(
                bodyText.contains(fullName),
                "Expected account overview body to contain persisted identity " + fullName
        );
        Assert.assertTrue(
                bodyText.contains("Profile"),
                "Expected account overview body to expose the profile continuation entry."
        );
    }

    private String safeText(String selector) {
        try {
            return UIActions.getText(selector, selector);
        } catch (AssertionError ignored) {
            return "";
        }
    }
}
