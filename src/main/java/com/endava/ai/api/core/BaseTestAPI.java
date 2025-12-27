package com.endava.ai.api.core;

import com.endava.ai.api.client.ApiClient;
import com.endava.ai.api.config.ConfigManager;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners({
        TestListener.class,
        AllureTestNg.class
})
public abstract class BaseTestAPI {

    @BeforeClass(alwaysRun = true)
    public void baseSetup() {
        ApiClient.init();
        requireTokenForWrites();
    }

    protected void requireTokenForWrites() {
        String token = ConfigManager.get("auth.token");
        if (token == null || token.isBlank()) {
            throw new SkipException("auth.token is not set; GoRest write operations require a token");
        }
    }
}
