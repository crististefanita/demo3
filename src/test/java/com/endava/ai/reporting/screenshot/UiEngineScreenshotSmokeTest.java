package com.endava.ai.reporting.screenshot;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.ui.engine.UIEngine;
import com.endava.ai.ui.engine.UIEngineFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UiEngineScreenshotSmokeTest {

    private static final String UI_WAIT_TIMEOUT_SECONDS = "ui.wait.timeout.seconds";

    private String previousUiEngine;
    private String previousUiHeadless;
    private String previousWindowMode;
    private String previousWindowSize;
    private String previousPageZoomPercent;
    private String previousUiWaitTimeoutSeconds;

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

        if (previousUiWaitTimeoutSeconds == null) {
            ConfigManager.clear(UI_WAIT_TIMEOUT_SECONDS);
        } else {
            ConfigManager.set(UI_WAIT_TIMEOUT_SECONDS, previousUiWaitTimeoutSeconds);
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
        previousWindowMode = ConfigManager.get("ui.window.mode", null);
        previousWindowSize = ConfigManager.get("ui.window.size", null);
        previousPageZoomPercent = ConfigManager.get("ui.page.zoom.percent", null);
        previousUiWaitTimeoutSeconds = ConfigManager.get(UI_WAIT_TIMEOUT_SECONDS, null);
        ConfigManager.set("ui.engine", engineName);
        ConfigManager.set("ui.headless", Boolean.toString(headless));
        ConfigManager.clear("ui.window.mode");
        ConfigManager.clear("ui.window.size");
        ConfigManager.clear("ui.page.zoom.percent");
        ConfigManager.set(UI_WAIT_TIMEOUT_SECONDS, "10");

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
        previousUiEngine = ConfigManager.get("ui.engine", null);
        previousUiHeadless = ConfigManager.get("ui.headless", null);
        previousWindowMode = ConfigManager.get("ui.window.mode", null);
        previousWindowSize = ConfigManager.get("ui.window.size", null);
        previousPageZoomPercent = ConfigManager.get("ui.page.zoom.percent", null);
        previousUiWaitTimeoutSeconds = ConfigManager.get(UI_WAIT_TIMEOUT_SECONDS, null);
        ConfigManager.set("ui.engine", engineName);
        ConfigManager.set("ui.headless", Boolean.toString(headless));
        ConfigManager.clear("ui.window.mode");
        ConfigManager.set("ui.window.size", "1920x1080");
        ConfigManager.clear("ui.page.zoom.percent");
        ConfigManager.set(UI_WAIT_TIMEOUT_SECONDS, "10");

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

    @Test(dataProvider = "engines")
    public void engine_accepts_fullscreen_window_mode_when_window_size_is_missing(String engineName, boolean headless) {
        previousUiEngine = ConfigManager.get("ui.engine", null);
        previousUiHeadless = ConfigManager.get("ui.headless", null);
        previousWindowMode = ConfigManager.get("ui.window.mode", null);
        previousWindowSize = ConfigManager.get("ui.window.size", null);
        previousPageZoomPercent = ConfigManager.get("ui.page.zoom.percent", null);
        previousUiWaitTimeoutSeconds = ConfigManager.get(UI_WAIT_TIMEOUT_SECONDS, null);
        ConfigManager.set("ui.engine", engineName);
        ConfigManager.set("ui.headless", Boolean.toString(headless));
        ConfigManager.set("ui.window.mode", "fullscreen");
        ConfigManager.clear("ui.window.size");
        ConfigManager.clear("ui.page.zoom.percent");
        ConfigManager.set(UI_WAIT_TIMEOUT_SECONDS, "10");

        UIEngine engine = UIEngineFactory.create();
        try {
            engine.open(ConfigManager.require("base.url"));
            String screenshot = engine.captureScreenshotAsBase64();

            Assert.assertNotNull(screenshot);
            Assert.assertFalse(
                    screenshot.isBlank(),
                    "Screenshot must not be blank for ui.window.mode=fullscreen on "
                            + engineName + " headless=" + headless
            );
        } finally {
            engine.quit();
        }
    }

    @Test(dataProvider = "engines")
    public void engine_accepts_custom_page_zoom_percent(String engineName, boolean headless) {
        previousUiEngine = ConfigManager.get("ui.engine", null);
        previousUiHeadless = ConfigManager.get("ui.headless", null);
        previousWindowMode = ConfigManager.get("ui.window.mode", null);
        previousWindowSize = ConfigManager.get("ui.window.size", null);
        previousPageZoomPercent = ConfigManager.get("ui.page.zoom.percent", null);
        previousUiWaitTimeoutSeconds = ConfigManager.get(UI_WAIT_TIMEOUT_SECONDS, null);
        ConfigManager.set("ui.engine", engineName);
        ConfigManager.set("ui.headless", Boolean.toString(headless));
        ConfigManager.set("ui.window.mode", "fullscreen");
        ConfigManager.clear("ui.window.size");
        ConfigManager.set("ui.page.zoom.percent", "67");
        ConfigManager.set(UI_WAIT_TIMEOUT_SECONDS, "10");

        UIEngine engine = UIEngineFactory.create();
        try {
            engine.open(ConfigManager.require("base.url"));
            String screenshot = engine.captureScreenshotAsBase64();

            Assert.assertNotNull(screenshot);
            Assert.assertFalse(
                    screenshot.isBlank(),
                    "Screenshot must not be blank for ui.page.zoom.percent=67 on "
                            + engineName + " headless=" + headless
            );
        } finally {
            engine.quit();
        }
    }
}
