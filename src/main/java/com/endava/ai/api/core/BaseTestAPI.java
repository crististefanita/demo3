package com.endava.ai.api.core;

import com.endava.ai.api.client.ApiClient;
import com.endava.ai.core.config.ConfigManager;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners({
        io.qameta.allure.testng.AllureTestNg.class,
        com.endava.ai.core.TestListener.class
})
public abstract class BaseTestAPI {

    @BeforeClass(alwaysRun = true)
    public void baseSetup() {
        ApiClient.init();
        requireTokenForWrites();
    }

    protected void requireTokenForWrites() {
        String token = ConfigManager.require("auth.token");
        if (token.isBlank()) {
            throw new SkipException("auth.token is not set; GoRest write operations require a token");
        }
    }
}
