package com.endava.ai.ui.service;

import com.endava.ai.ui.pages.ProfilePage;
import com.endava.ai.ui.utils.UIActions;
import com.endava.ai.ui.utils.WaitUtils;

public final class ProfileService {

    public void openProfileForm() {
        UIActions.click(ProfilePage.NAV_PROFILE, "Profile");
        WaitUtils.waitForVisible(ProfilePage.UPDATE_PROFILE_BUTTON);
    }
}
