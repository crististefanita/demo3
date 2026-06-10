package com.endava.ai.ui.validation;

import com.endava.ai.ui.model.CustomerData;
import com.endava.ai.ui.pages.ProfilePage;
import com.endava.ai.ui.utils.UIActions;
import com.endava.ai.ui.utils.WaitUtils;
import org.testng.Assert;

public final class ProfileValidation {

    public void assertProfileLoaded() {
        WaitUtils.waitForCondition(
                "profile first name is visible",
                () -> !UIActions.getValue(ProfilePage.FIRST_NAME, "Profile first name").isBlank(),
                "Expected profile surface to load first name data"
        );
    }

    public void assertProfileMatches(CustomerData customer) {
        assertFieldEquals(ProfilePage.FIRST_NAME, "Profile first name", customer.getFirstName());
        assertFieldEquals(ProfilePage.LAST_NAME, "Profile last name", customer.getLastName());
        assertFieldEquals(ProfilePage.EMAIL, "Profile email", customer.getEmail());
        assertFieldEquals(ProfilePage.PHONE, "Profile phone", customer.getPhone());
        assertFieldEquals(ProfilePage.STREET, "Profile street", customer.getStreet());
        assertFieldEquals(ProfilePage.CITY, "Profile city", customer.getCity());
        assertFieldEquals(ProfilePage.STATE, "Profile state", customer.getState());
        assertFieldEquals(ProfilePage.POSTAL_CODE, "Profile postal code", customer.getPostalCode());
    }

    public void assertPasswordChangedSuccessfully() {
        WaitUtils.waitForCondition(
                "password change success banner is visible",
                () -> {
                    String text = UIActions.getText(ProfilePage.PASSWORD_SUCCESS, "Password change success banner");
                    return text != null && !text.trim().isEmpty();
                },
                "Expected password change success banner to be present"
        );

        String text = UIActions.getText(ProfilePage.PASSWORD_SUCCESS, "Password change success banner");
        Assert.assertTrue(
                text.toLowerCase().contains("success"),
                "Expected password change success banner to contain success wording, but was: " + text
        );
    }

    private void assertFieldEquals(String locator, String description, String expectedValue) {
        WaitUtils.waitForInputValue(locator, description, expectedValue::equals);
        String actualValue = UIActions.getValue(locator, description);
        Assert.assertEquals(actualValue, expectedValue, "Unexpected value for " + description);
    }
}
