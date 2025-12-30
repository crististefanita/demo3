package com.endava.ai.ui.engine.selenium;

import com.endava.ai.ui.engine.UIEngine;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public final class SeleniumEngine implements UIEngine {
    private final WebDriver driver;

    public SeleniumEngine() {
        ChromeOptions options = new ChromeOptions();
        // Headless by default for CI friendliness; remove if you prefer UI.
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        this.driver = new ChromeDriver(options);
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
    }

    @Override
    public boolean supportsAutoWait() { return false; }

    @Override
    public void setWindowSize(int width, int height) {
        driver.manage().window().setSize(new Dimension(width, height));
    }

    @Override
    public void open(String url) {
        driver.get(url);
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
}
