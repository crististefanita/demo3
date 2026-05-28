package com.endava.ai.ui.engine.selenium;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.ui.engine.UIEngine;
import com.endava.ai.ui.engine.window.UIWindowConfiguration;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public final class SeleniumEngine implements UIEngine {
    private final WebDriver driver;
    private final UIWindowConfiguration windowConfiguration;
    private boolean browserZoomConfigured;

    public SeleniumEngine(UIWindowConfiguration windowConfiguration) {
        this.windowConfiguration = windowConfiguration;
        ChromeOptions options = new ChromeOptions();
        if (isHeadless()) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        this.driver = new ChromeDriver(options);
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        applyWindowConfiguration(windowConfiguration);
        assertSingleStartupTarget("after init");
    }

    @Override
    public boolean supportsAutoWait() { return false; }

    @Override
    public void setWindowSize(int width, int height) {
        driver.manage().window().setSize(new Dimension(width, height));
    }

    @Override
    public void open(String url) {
        ensureBrowserZoomConfigured();
        driver.get(url);
        assertSingleStartupTarget("after open");
    }

    @Override
    public void click(String cssSelector) {
        WebElement el = find(cssSelector);
        el.click();
    }

    @Override
    public void type(String cssSelector, String text) {
        WebElement el = find(cssSelector);
        el.clear();
        el.sendKeys(text);
    }

    @Override
    public void select(String cssSelector, String valueOrText) {
        Select select = new Select(find(cssSelector));

        try {
            select.selectByValue(valueOrText);
            return;
        } catch (NoSuchElementException ignored) {
        }

        try {
            select.selectByVisibleText(valueOrText);
            return;
        } catch (NoSuchElementException ignored) {
        }

        for (WebElement option : select.getOptions()) {
            if (valueOrText.equalsIgnoreCase(option.getText().trim())) {
                option.click();
                return;
            }
        }

        throw new NoSuchElementException("No option found for: " + valueOrText);
    }

    @Override
    public String getText(String cssSelector) {
        List<WebElement> els = driver.findElements(By.cssSelector(cssSelector));
        if (els.isEmpty()) return "";
        if (els.size() == 1) return els.get(0).getText();
        StringBuilder sb = new StringBuilder();
        for (WebElement e : els) {
            String t = e.getText();
            if (t != null && !t.trim().isEmpty()) {
                if (sb.length() > 0) sb.append("\n");
                sb.append(t.trim());
            }
        }
        return sb.toString();
    }

    @Override
    public String getValue(String cssSelector) {
        return find(cssSelector).getAttribute("value");
    }

    @Override
    public boolean isVisible(String cssSelector) {
        try {
            WebElement el = driver.findElement(By.cssSelector(cssSelector));
            return el.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public void waitForVisible(String cssSelector, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssSelector)));
    }

    @Override
    public void waitForUrlContains(String fragment, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public void clearSession() {
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript(
                "window.localStorage.clear(); window.sessionStorage.clear();"
        );
    }

    @Override
    public String captureScreenshotAsBase64() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
    }

    @Override
    public void quit() {
        driver.quit();
    }

    private WebElement find(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector));
    }

    private void applyWindowConfiguration(UIWindowConfiguration windowConfiguration) {
        if (windowConfiguration.isFullscreen() && !isHeadless()) {
            driver.manage().window().maximize();
            return;
        }

        setWindowSize(windowConfiguration.width(), windowConfiguration.height());
    }

    private void ensureBrowserZoomConfigured() {
        if (browserZoomConfigured || windowConfiguration.zoomPercent() == UIWindowConfiguration.DEFAULT_ZOOM_PERCENT) {
            return;
        }

        driver.get("chrome://settings/appearance");
        Object hasSettingsPrivate = ((JavascriptExecutor) driver)
                .executeScript("return typeof chrome !== 'undefined' && !!chrome.settingsPrivate;");
        if (!Boolean.TRUE.equals(hasSettingsPrivate)) {
            throw new IllegalStateException("chrome.settingsPrivate is not available for browser zoom configuration");
        }

        double expectedZoomFactor = windowConfiguration.zoomPercent() / 100.0d;
        ((JavascriptExecutor) driver).executeScript(
                "chrome.settingsPrivate.setDefaultZoom(arguments[0]);",
                expectedZoomFactor
        );

        Object configuredZoom = ((JavascriptExecutor) driver).executeAsyncScript(
                "const done = arguments[arguments.length - 1];"
                        + "chrome.settingsPrivate.getDefaultZoom(done);"
        );
        if (!(configuredZoom instanceof Number)
                || Math.abs(((Number) configuredZoom).doubleValue() - expectedZoomFactor) > 0.01d) {
            throw new IllegalStateException("Browser zoom was not applied correctly");
        }

        browserZoomConfigured = true;
    }

    private void assertSingleStartupTarget(String phase) {
        int handleCount = driver.getWindowHandles().size();
        logStartupDebug("Selenium window handles " + phase + ": " + handleCount);
        if (handleCount != 1) {
            throw new IllegalStateException("Expected exactly one Selenium window " + phase + " but found " + handleCount);
        }
    }

    private static void logStartupDebug(String message) {
        if (Boolean.parseBoolean(ConfigManager.get("console.details.enabled", "false"))) {
            System.out.println("  • " + message);
        }
    }

    private static boolean isHeadless() {
        return Boolean.parseBoolean(ConfigManager.get("ui.headless", "true"));
    }
}
