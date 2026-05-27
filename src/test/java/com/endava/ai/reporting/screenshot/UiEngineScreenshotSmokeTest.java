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
    private String previousWindowMode;
    private String previousWindowSize;
    private String previousPageZoomPercent;
    private String previousExplicitWaitSeconds;

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

        if (previousWindowMode == null) {
            ConfigManager.clear("ui.window.mode");
        } else {
            ConfigManager.set("ui.window.mode", previousWindowMode);
        }

        if (previousWindowSize == null) {
            ConfigManager.clear("ui.window.size");
        } else {
            ConfigManager.set("ui.window.size", previousWindowSize);
        }

        if (previousPageZoomPercent == null) {
            ConfigManager.clear("ui.page.zoom.percent");
        } else {
            ConfigManager.set("ui.page.zoom.percent", previousPageZoomPercent);
        }

        if (previousExplicitWaitSeconds == null) {
            ConfigManager.clear("explicit.wait.seconds");
        } else {
            ConfigManager.set("explicit.wait.seconds", previousExplicitWaitSeconds);
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
        configureEngine(engineName, headless, null, null, null);

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

    @Test(dataProvider = "engines")
    public void engine_accepts_custom_window_size_configuration(String engineName, boolean headless) {
        configureEngine(engineName, headless, null, "1920x1080", null);

        UIEngine engine = UIEngineFactory.create();
        try {
            engine.open(ConfigManager.require("base.url"));
            String screenshot = engine.captureScreenshotAsBase64();

            Assert.assertNotNull(screenshot);
            Assert.assertFalse(
                    screenshot.isBlank(),
                    "Screenshot must not be blank for custom ui.window.size on "
                            + engineName + " headless=" + headless
            );
        } finally {
            engine.quit();
        }
    }

    private void configureEngine(
            String engineName,
            boolean headless,
            String windowMode,
            String windowSize,
            String pageZoomPercent
    ) {
        previousUiEngine = ConfigManager.get("ui.engine", null);
        previousUiHeadless = ConfigManager.get("ui.headless", null);
        previousWindowMode = ConfigManager.get("ui.window.mode", null);
        previousWindowSize = ConfigManager.get("ui.window.size", null);
        previousPageZoomPercent = ConfigManager.get("ui.page.zoom.percent", null);
        previousExplicitWaitSeconds = ConfigManager.get("explicit.wait.seconds", null);
        ConfigManager.set("ui.engine", engineName);
        ConfigManager.set("ui.headless", Boolean.toString(headless));
        if (windowMode == null) {
            ConfigManager.clear("ui.window.mode");
        } else {
            ConfigManager.set("ui.window.mode", windowMode);
        }
        if (windowSize == null) {
            ConfigManager.clear("ui.window.size");
        } else {
            ConfigManager.set("ui.window.size", windowSize);
        }
        if (pageZoomPercent == null) {
            ConfigManager.clear("ui.page.zoom.percent");
        } else {
            ConfigManager.set("ui.page.zoom.percent", pageZoomPercent);
        }
        ConfigManager.set("explicit.wait.seconds", "10");
    }
}
