package com.endava.ai.reporting.screenshot;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.ui.engine.UIEngine;
import com.endava.ai.ui.engine.UIEngineFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UiEngineScreenshotSmokeTest {

    private String previousUiEngine;
    private String previousUiHeadless;

    @AfterMethod
    public void tearDown() {
        if (previousUiEngine == null) {
            ConfigManager.clear("ui.engine");
        } else {
            ConfigManager.set("ui.engine", previousUiEngine);
        }

        if (previousUiHeadless == null) {
            ConfigManager.clear("ui.headless");
        } else {
            ConfigManager.set("ui.headless", previousUiHeadless);
        }
    }

    @DataProvider
    public Object[][] engines() {
        return new Object[][]{
                {"selenium", true},
                {"selenium", false},
                {"playwright", true},
                {"playwright", false}
        };
    }

    @Test(dataProvider = "engines")
    public void engine_can_open_base_url_and_capture_screenshot(String engineName, boolean headless) {
        previousUiEngine = ConfigManager.get("ui.engine", null);
        previousUiHeadless = ConfigManager.get("ui.headless", null);
        ConfigManager.set("ui.engine", engineName);
        ConfigManager.set("ui.headless", Boolean.toString(headless));

        UIEngine engine = UIEngineFactory.create();
        try {
            engine.open(ConfigManager.require("base.url"));
            String screenshot = engine.captureScreenshotAsBase64();

            Assert.assertNotNull(screenshot);
            Assert.assertFalse(
                    screenshot.isBlank(),
                    "Screenshot must not be blank for " + engineName + " headless=" + headless
            );
        } finally {
            engine.quit();
        }
    }
}
