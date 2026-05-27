package com.endava.ai.reporting.screenshot;

import com.endava.ai.core.config.ConfigManager;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.BrowserContext;
import com.endava.ai.ui.engine.UIEngine;
import com.endava.ai.ui.engine.UIEngineFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

public class UiEngineBrowserWindowContractSmokeTest {

    private String previousUiEngine;
    private String previousUiHeadless;
    private String previousWindowMode;
    private String previousWindowSize;
    private String previousPageZoomPercent;
    private String previousExplicitWaitSeconds;

    @AfterMethod
    public void tearDown() {
        restore("ui.engine", previousUiEngine);
        restore("ui.headless", previousUiHeadless);
        restore("ui.window.mode", previousWindowMode);
        restore("ui.window.size", previousWindowSize);
        restore("ui.page.zoom.percent", previousPageZoomPercent);
        restore("explicit.wait.seconds", previousExplicitWaitSeconds);
    }

    @DataProvider
    public Object[][] engines() {
        return new Object[][]{
                {"selenium"},
                {"playwright"}
        };
    }

    @Test(dataProvider = "engines")
    public void engine_keeps_single_browser_target_during_startup_and_navigation(String engineName) throws Exception {
        snapshot();
        ConfigManager.set("ui.engine", engineName);
        ConfigManager.set("ui.headless", "false");
        ConfigManager.set("ui.window.mode", "fullscreen");
        ConfigManager.clear("ui.window.size");
        ConfigManager.set("ui.page.zoom.percent", "60");
        ConfigManager.set("explicit.wait.seconds", "10");

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

    private static int countBrowserTargets(UIEngine engine) throws Exception {
        if (engine.getClass().getName().contains("SeleniumEngine")) {
            org.openqa.selenium.WebDriver driver = (org.openqa.selenium.WebDriver) readField(engine, "driver");
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
        previousExplicitWaitSeconds = ConfigManager.get("explicit.wait.seconds", null);
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
