package com.endava.ai.ui.service;

import com.endava.ai.ui.utils.UIActions;

public final class AccountService {

    public void openAccountOverview() {
        UIActions.navigateToRelative("/account");
    }
}
