package com.endava.ai.ui.validation;

import com.endava.ai.ui.model.RegistrationData;
import com.endava.ai.ui.pages.ProfilePage;
import com.endava.ai.ui.utils.UIActions;
import com.endava.ai.ui.utils.WaitUtils;
import org.testng.Assert;

public final class ProfileValidation {

    public void assertProfileFormPrefilled(RegistrationData expected) {
        WaitUtils.waitForInputValue(
                ProfilePage.FIRST_NAME,
                "Profile first name",
                expected.firstName()::equals
        );

        assertFieldValue(ProfilePage.FIRST_NAME, "Profile first name", expected.firstName());
        assertFieldValue(ProfilePage.LAST_NAME, "Profile last name", expected.lastName());
        assertFieldValue(ProfilePage.EMAIL, "Profile email", expected.email());
        assertFieldValue(ProfilePage.PHONE, "Profile phone", expected.phone());
        assertFieldValue(ProfilePage.STREET, "Profile street", expected.street());
        assertFieldValue(ProfilePage.CITY, "Profile city", expected.city());
        assertFieldValue(ProfilePage.STATE, "Profile state", expected.state());
        assertFieldValue(ProfilePage.POSTAL_CODE, "Profile postal code", expected.postal());
        assertFieldValue(ProfilePage.COUNTRY, "Profile country", expected.country());

        String cta = UIActions.getText(ProfilePage.UPDATE_PROFILE_BUTTON, "Update Profile button");
        Assert.assertTrue(
                cta.contains("Update Profile"),
                "Expected profile form to expose the update action."
        );
    }

    private void assertFieldValue(String selector, String description, String expected) {
        String actual = UIActions.getValue(selector, description);
        Assert.assertEquals(actual, expected, "Unexpected prefilled value for " + description);
    }
}
