package com.endava.ai.ui.service;

import com.endava.ai.ui.pages.AccountPage;
import com.endava.ai.ui.utils.UIActions;

public final class FavoritesService {

    public void openFavoritesFromAccount() {
        UIActions.click(AccountPage.NAV_FAVORITES, "Favorites");
    }
}
