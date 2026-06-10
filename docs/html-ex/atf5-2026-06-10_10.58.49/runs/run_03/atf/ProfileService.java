package com.endava.ai.ui.service;

import com.endava.ai.ui.pages.AccountPage;
import com.endava.ai.ui.pages.ProfilePage;
import com.endava.ai.ui.utils.UIActions;
import com.endava.ai.ui.utils.WaitUtils;

public final class ProfileService {

    public void openAccount() {
        UIActions.navigateToRelative("/account");
        WaitUtils.waitForVisible(AccountPage.NAV_PROFILE);
    }

    public void openProfile() {
        openAccount();
        openProfileFromAccount();
    }

    public void openProfileFromAccount() {
        UIActions.click(AccountPage.NAV_PROFILE, "Profile");
        WaitUtils.waitForVisible(ProfilePage.FIRST_NAME);
    }

    public void changePassword(String currentPassword, String newPassword) {
        changePassword(currentPassword, newPassword, newPassword);
    }

    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        UIActions.type(ProfilePage.CURRENT_PASSWORD, "Current password", currentPassword);
        UIActions.type(ProfilePage.NEW_PASSWORD, "New password", newPassword);
        UIActions.type(ProfilePage.NEW_PASSWORD_CONFIRM, "Confirm new password", confirmPassword);
        UIActions.click(ProfilePage.CHANGE_PASSWORD_SUBMIT, "Change password");
    }
}
