package com.endava.ai.ui.engine.playwright;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.ui.engine.UIEngine;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.Base64;
import java.util.Objects;
import java.util.stream.Collectors;

public final class PlaywrightEngine implements UIEngine {

    private static final int EXPLICIT_WAIT_SECONDS =
            Integer.parseInt(Objects.requireNonNull(ConfigManager.get("explicit.wait.seconds", "10")));

    private final Playwright playwright;
    private final Browser browser;
    private final BrowserContext context;
    private final Page page;

    public PlaywrightEngine() {
        this.playwright = Playwright.create();
        this.browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(true));
        this.context = browser.newContext();
        this.page = context.newPage();

        int timeoutMs = EXPLICIT_WAIT_SECONDS * 1000;
        page.setDefaultTimeout(timeoutMs);
        page.setDefaultNavigationTimeout(timeoutMs);
    }

    @Override
    public boolean supportsAutoWait() {
        return true;
    }

    @Override
    public void setWindowSize(int width, int height) {
        page.setViewportSize(width, height);
    }

    @Override
    public void open(String url) {
        page.navigate(url);
    }

    @Override
    public void click(String cssSelector) {
        // Playwright auto-wait applies (uses default timeout)
        page.locator(cssSelector).first().click();
    }

    @Override
    public void type(String cssSelector, String text) {
        Locator loc = page.locator(cssSelector).first();
        loc.fill("");
        loc.type(text);
    }

    @Override
    public String getText(String cssSelector) {
        Locator loc = page.locator(cssSelector);
        if (loc.count() == 0) {
            return "";
        }
        return loc.allInnerTexts().stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean isVisible(String cssSelector) {
        return page.locator(cssSelector).first().isVisible();
    }

    @Override
    public void waitForVisible(String cssSelector, int seconds) {
        // explicit wait override (still allowed)
        page.locator(cssSelector).first().waitFor(new Locator.WaitForOptions()
                .setTimeout(seconds * 1000.0)
                .setState(WaitForSelectorState.VISIBLE));
    }

    @Override
    public void waitForUrlContains(String fragment, int seconds) {
        page.waitForURL(
                "**" + fragment + "**",
                new Page.WaitForURLOptions().setTimeout(seconds * 1000.0)
        );
    }

    @Override
    public String getCurrentUrl() {
        return page.url();
    }

    @Override
    public String captureScreenshotAsBase64() {
        byte[] bytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public void quit() {
        context.close();
        browser.close();
        playwright.close();
    }
}
