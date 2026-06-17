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

import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class SeleniumEngine implements UIEngine {
    private static final String UI_WAIT_TIMEOUT_SECONDS_KEY = "ui.wait.timeout.seconds";
    private static final String LEGACY_WAIT_TIMEOUT_SECONDS_KEY = "explicit.wait.seconds";
    private static final int UI_WAIT_TIMEOUT_SECONDS =
            Integer.parseInt(Objects.requireNonNull(
                    ConfigManager.get(
                            UI_WAIT_TIMEOUT_SECONDS_KEY,
                            ConfigManager.get(LEGACY_WAIT_TIMEOUT_SECONDS_KEY, "10")
                    )
            ));
    private static final Duration IMPLICIT_WAIT_TIMEOUT = Duration.ofSeconds(0);
    private static final int TEXT_READ_ATTEMPTS = 4;
    private static final int TYPE_ATTEMPTS = 2;
    private static final long RETRY_SLEEP_MILLIS = 150L;
    private static final double ZOOM_TOLERANCE = 0.01d;
    private static final String XPATH_PREFIX = "xpath=";

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
        this.driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT);
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
        By locator = by(cssSelector);
        WebElement element = waitForClickable(locator);
        clickWithFallback(element);
    }

    @Override
    public void clickVisible(String cssSelector, int oneBasedIndex) {
        if (oneBasedIndex < 1) {
            throw new IllegalArgumentException("Visible click index is 1-based.");
        }

        List<WebElement> visibleElements = driver.findElements(by(cssSelector)).stream()
                .filter(WebElement::isDisplayed)
                .collect(Collectors.toList());
        if (visibleElements.size() < oneBasedIndex) {
            throw new NoSuchElementException("No visible match found for index " + oneBasedIndex + " using selector: " + cssSelector);
        }

        WebElement element = visibleElements.get(oneBasedIndex - 1);
        clickWithFallback(element);
    }

    @Override
    public void type(String cssSelector, String text) {
        typeInternal(cssSelector, text, true);
    }

    @Override
    public void pressKey(String cssSelector, String key) {
        WebElement element = find(cssSelector);
        scrollIntoView(element);
        element.sendKeys(mapKey(key));
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
    public void uploadFile(String cssSelector, String absolutePath) {
        find(cssSelector).sendKeys(Path.of(absolutePath).toAbsolutePath().toString());
    }

    @Override
    public String getText(String cssSelector) {
        List<WebElement> els = driver.findElements(by(cssSelector));
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
    public List<String> getTexts(String cssSelector) {
        return readTexts(cssSelector, false);
    }

    @Override
    public List<String> getVisibleTexts(String cssSelector) {
        return readTexts(cssSelector, true);
    }

    @Override
    public String getValue(String cssSelector) {
        WebElement element = find(cssSelector);
        String value = element.getDomProperty("value");
        return value != null ? value : element.getAttribute("value");
    }

    @Override
    public String getAttribute(String cssSelector, String attributeName) {
        return find(cssSelector).getAttribute(attributeName);
    }

    @Override
    public boolean isVisible(String cssSelector) {
        try {
            WebElement el = driver.findElement(by(cssSelector));
            return el.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public boolean isEnabled(String cssSelector) {
        try {
            return driver.findElement(by(cssSelector)).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public void waitForVisible(String cssSelector, int seconds) {
        wait(seconds).until(ExpectedConditions.visibilityOfElementLocated(by(cssSelector)));
    }

    @Override
    public void waitForUrlContains(String fragment, int seconds) {
        wait(seconds).until(ExpectedConditions.urlContains(fragment));
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public void clearSession() {
        driver.manage().deleteAllCookies();
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "window.localStorage.clear(); window.sessionStorage.clear();"
            );
        } catch (WebDriverException ignored) {
        }
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
        List<WebElement> matches = driver.findElements(by(cssSelector));
        if (matches.isEmpty()) {
            throw new NoSuchElementException("No element found for selector: " + cssSelector);
        }

        for (WebElement match : matches) {
            if (match.isDisplayed()) {
                return match;
            }
        }

        return matches.get(0);
    }

    private void scrollIntoView(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});",
                    element
            );
        } catch (WebDriverException ignored) {
        }
    }

    private void sleepBriefly() {
        try {
            Thread.sleep(RETRY_SLEEP_MILLIS);
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
        }
    }

    private void typeInternal(String cssSelector, String text, boolean allowRetry) {
        int attempts = allowRetry ? TYPE_ATTEMPTS : 1;
        for (int attempt = 0; attempt < attempts; attempt++) {
            try {
                WebElement element = find(cssSelector);
                scrollIntoView(element);
                element.clear();
                element.sendKeys(text);
                return;
            } catch (StaleElementReferenceException stale) {
                if (attempt == attempts - 1) {
                    throw stale;
                }
                sleepBriefly();
            }
        }
    }

    private WebDriverWait wait(int seconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    private WebElement waitForClickable(By locator) {
        return wait(UI_WAIT_TIMEOUT_SECONDS)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void clickWithFallback(WebElement element) {
        try {
            element.click();
        } catch (WebDriverException clickFailure) {
            scrollIntoView(element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    private List<String> readTexts(String cssSelector, boolean visibleOnly) {
        for (int attempt = 0; attempt < TEXT_READ_ATTEMPTS; attempt++) {
            try {
                List<String> values = new ArrayList<>();
                for (WebElement element : driver.findElements(by(cssSelector))) {
                    if (visibleOnly && !element.isDisplayed()) {
                        continue;
                    }
                    String text = element.getText();
                    if (text != null && !text.trim().isEmpty()) {
                        values.add(text.trim());
                    }
                }
                return values;
            } catch (StaleElementReferenceException stale) {
                if (attempt == TEXT_READ_ATTEMPTS - 1) {
                    throw stale;
                }
                sleepBriefly();
            }
        }
        return List.of();
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
                || Math.abs(((Number) configuredZoom).doubleValue() - expectedZoomFactor) > ZOOM_TOLERANCE) {
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

    private static CharSequence mapKey(String key) {
        if ("Tab".equalsIgnoreCase(key)) {
            return Keys.TAB;
        }
        if ("Enter".equalsIgnoreCase(key)) {
            return Keys.ENTER;
        }
        if ("ArrowRight".equalsIgnoreCase(key)) {
            return Keys.ARROW_RIGHT;
        }
        if ("ArrowLeft".equalsIgnoreCase(key)) {
            return Keys.ARROW_LEFT;
        }
        throw new IllegalArgumentException("Unsupported key for Selenium engine: " + key);
    }

    private static By by(String selector) {
        if (selector.startsWith(XPATH_PREFIX)) {
            return By.xpath(selector.substring(XPATH_PREFIX.length()));
        }
        if (selector.startsWith("//")) {
            return By.xpath(selector);
        }
        return By.cssSelector(selector);
    }
}
