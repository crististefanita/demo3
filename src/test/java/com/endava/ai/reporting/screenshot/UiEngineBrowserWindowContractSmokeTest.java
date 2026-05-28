package com.endava.ai.reporting.screenshot;

import com.endava.ai.core.config.ConfigManager;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.BrowserContext;
import com.endava.ai.ui.engine.UIEngine;
import com.endava.ai.ui.engine.UIEngineFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

public class UiEngineBrowserWindowContractSmokeTest {

    private static final String UI_WAIT_TIMEOUT_SECONDS = "ui.wait.timeout.seconds";

    private String previousUiEngine;
    private String previousUiHeadless;
    private String previousWindowMode;
    private String previousWindowSize;
    private String previousPageZoomPercent;
    private String previousUiWaitTimeoutSeconds;

    @AfterMethod
    public void tearDown() {
        restore("ui.engine", previousUiEngine);
        restore("ui.headless", previousUiHeadless);
        restore("ui.window.mode", previousWindowMode);
        restore("ui.window.size", previousWindowSize);
        restore("ui.page.zoom.percent", previousPageZoomPercent);
        restore(UI_WAIT_TIMEOUT_SECONDS, previousUiWaitTimeoutSeconds);
    }

    @DataProvider
    public Object[][] engines() {
        return new Object[][]{
                {"selenium"},
                {"playwright"}
        };
    }

    @Test(dataProvider = "engines")
    public void configured_browser_zoom_changes_device_pixel_ratio(String engineName) throws Exception {
        double baseline = observeDevicePixelRatio(engineName, "100");
        double zoomed = observeDevicePixelRatio(engineName, "60");

        Assert.assertTrue(
                zoomed < baseline * 0.85d,
                "Zoomed devicePixelRatio must be meaningfully lower for " + engineName
        );
    }

    @Test(dataProvider = "engines")
    public void engine_keeps_single_browser_target_during_startup_and_navigation(String engineName) throws Exception {
        snapshot();
        ConfigManager.set("ui.engine", engineName);
        ConfigManager.set("ui.headless", "false");
        ConfigManager.set("ui.window.mode", "fullscreen");
        ConfigManager.clear("ui.window.size");
        ConfigManager.set("ui.page.zoom.percent", "60");
        ConfigManager.set(UI_WAIT_TIMEOUT_SECONDS, "10");

        UIEngine engine = UIEngineFactory.create();
        try {
            Assert.assertEquals(countBrowserTargets(engine), 1, "Engine must start with a single browser target");
            engine.open(ConfigManager.require("base.url") + "/auth/login");
            Assert.assertEquals(countBrowserTargets(engine), 1, "Engine must keep a single browser target after open");
            Assert.assertTrue(engine.getCurrentUrl().contains("/auth/login"));
        } finally {
            engine.quit();
        }
    }

    private double observeDevicePixelRatio(String engineName, String zoomPercent) throws Exception {
        snapshot();
        ConfigManager.set("ui.engine", engineName);
        ConfigManager.set("ui.headless", "false");
        ConfigManager.set("ui.window.mode", "fullscreen");
        ConfigManager.clear("ui.window.size");
        ConfigManager.set("ui.page.zoom.percent", zoomPercent);
        ConfigManager.set(UI_WAIT_TIMEOUT_SECONDS, "10");

        UIEngine engine = UIEngineFactory.create();
        try {
            engine.open(ConfigManager.require("base.url") + "/auth/login");
            return readDevicePixelRatio(engine);
        } finally {
            engine.quit();
            tearDown();
        }
    }

    private static double readDevicePixelRatio(UIEngine engine) throws Exception {
        Object devicePixelRatio;
        if (engine.getClass().getName().contains("SeleniumEngine")) {
            WebDriver driver = (WebDriver) readField(engine, "driver");
            devicePixelRatio = ((JavascriptExecutor) driver).executeScript("return window.devicePixelRatio;");
        } else {
            Page page = (Page) readField(engine, "page");
            devicePixelRatio = page.evaluate("() => window.devicePixelRatio");
        }
        Assert.assertTrue(devicePixelRatio instanceof Number);
        return ((Number) devicePixelRatio).doubleValue();
    }

    private static int countBrowserTargets(UIEngine engine) throws Exception {
        if (engine.getClass().getName().contains("SeleniumEngine")) {
            WebDriver driver = (WebDriver) readField(engine, "driver");
            return driver.getWindowHandles().size();
        }

        BrowserContext context = (BrowserContext) readField(engine, "context");
        return context.pages().size();
    }

    private void snapshot() {
        previousUiEngine = ConfigManager.get("ui.engine", null);
        previousUiHeadless = ConfigManager.get("ui.headless", null);
        previousWindowMode = ConfigManager.get("ui.window.mode", null);
        previousWindowSize = ConfigManager.get("ui.window.size", null);
        previousPageZoomPercent = ConfigManager.get("ui.page.zoom.percent", null);
        previousUiWaitTimeoutSeconds = ConfigManager.get(UI_WAIT_TIMEOUT_SECONDS, null);
    }

    private static Object readField(Object target, String fieldName) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(target);
    }

    private static void restore(String key, String value) {
        if (value == null) {
            ConfigManager.clear(key);
        } else {
            ConfigManager.set(key, value);
        }
    }
}
