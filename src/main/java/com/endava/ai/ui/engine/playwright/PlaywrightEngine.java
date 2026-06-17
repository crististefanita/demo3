package com.endava.ai.ui.engine.playwright;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.ui.engine.UIEngine;
import com.endava.ai.ui.engine.window.UIWindowConfiguration;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitUntilState;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PlaywrightEngine implements UIEngine {

    private static final String UI_WAIT_TIMEOUT_SECONDS_KEY = "ui.wait.timeout.seconds";
    private static final String LEGACY_WAIT_TIMEOUT_SECONDS_KEY = "explicit.wait.seconds";
    private static final int UI_WAIT_TIMEOUT_SECONDS =
            Integer.parseInt(Objects.requireNonNull(
                    ConfigManager.get(
                            UI_WAIT_TIMEOUT_SECONDS_KEY,
                            ConfigManager.get(LEGACY_WAIT_TIMEOUT_SECONDS_KEY, "10")
                    )
            ));

    private final Playwright playwright;
    private final Browser browser;
    private final BrowserContext context;
    private final Page page;
    private final UIWindowConfiguration windowConfiguration;
    private final boolean headless;
    private final Path userDataDir;
    private boolean browserZoomConfigured;

    public PlaywrightEngine(UIWindowConfiguration windowConfiguration) {
        this.windowConfiguration = windowConfiguration;
        boolean headless = isHeadless();
        this.headless = headless;
        this.playwright = Playwright.create();
        this.userDataDir = requiresPersistentContextForZoom(windowConfiguration)
                ? createTemporaryUserDataDir()
                : null;

        if (userDataDir == null) {
            this.browser = playwright.chromium()
                    .launch(createLaunchOptions(windowConfiguration, headless));
            this.context = browser.newContext(createContextOptions(windowConfiguration, headless));
        } else {
            this.context = playwright.chromium()
                    .launchPersistentContext(userDataDir, createPersistentContextOptions(windowConfiguration, headless));
            this.browser = context.browser();
        }
        this.page = acquireStartupPage(context);

        int timeoutMs = UI_WAIT_TIMEOUT_SECONDS * 1000;
        page.setDefaultTimeout(timeoutMs);
        page.setDefaultNavigationTimeout(timeoutMs);
        applyWindowConfiguration(windowConfiguration, headless);
        assertSingleStartupTarget("after init");
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
        if (!usesHeadlessZoomFallback()) {
            ensureBrowserZoomConfigured();
        }
        page.navigate(url, new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        if (usesHeadlessZoomFallback()) {
            applyHeadlessPageZoomFallback();
        }
        assertSingleStartupTarget("after open");
    }

    @Override
    public void click(String cssSelector) {
        Locator locator = page.locator(cssSelector).first();
        locator.scrollIntoViewIfNeeded();
        try {
            locator.click();
        } catch (PlaywrightException firstFailure) {
            locator.click(new Locator.ClickOptions().setForce(true));
        }
    }

    @Override
    public void clickVisible(String cssSelector, int oneBasedIndex) {
        if (oneBasedIndex < 1) {
            throw new IllegalArgumentException("Visible click index is 1-based.");
        }
        Locator locator = page.locator(cssSelector);
        int count = locator.count();
        int visibleIndex = 0;
        for (int i = 0; i < count; i++) {
            Locator nth = locator.nth(i);
            if (!nth.isVisible()) {
                continue;
            }
            visibleIndex++;
            if (visibleIndex == oneBasedIndex) {
                nth.click();
                return;
            }
        }
        throw new PlaywrightException("No visible match found for index " + oneBasedIndex + " using selector: " + cssSelector);
    }

    @Override
    public void type(String cssSelector, String text) {
        Locator loc = page.locator(cssSelector).first();
        loc.fill(text);
    }

    @Override
    public void pressKey(String cssSelector, String key) {
        Locator loc = page.locator(cssSelector).first();
        loc.press(key);
    }

    @Override
    public void select(String cssSelector, String valueOrText) {
        Locator loc = page.locator(cssSelector).first();
        List<String> selected = loc.selectOption(valueOrText);
        if (!selected.isEmpty()) return;

        selected = loc.selectOption(new SelectOption().setLabel(valueOrText));
        if (!selected.isEmpty()) return;

        selected = loc.selectOption(new SelectOption().setValue(valueOrText));
        if (!selected.isEmpty()) return;

        throw new PlaywrightException("No option found for: " + valueOrText);
    }

    @Override
    public void uploadFile(String cssSelector, String absolutePath) {
        page.locator(cssSelector).first().setInputFiles(Path.of(absolutePath));
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
    public List<String> getTexts(String cssSelector) {
        return page.locator(cssSelector).allInnerTexts().stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getVisibleTexts(String cssSelector) {
        Object raw = page.locator(cssSelector).evaluateAll(
                "elements => elements" +
                        ".filter(element => !!(element.offsetWidth || element.offsetHeight || element.getClientRects().length))" +
                        ".map(element => element.innerText.trim())" +
                        ".filter(text => text.length > 0)"
        );
        if (!(raw instanceof List<?>)) {
            return Collections.emptyList();
        }
        return ((List<?>) raw).stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    @Override
    public String getValue(String cssSelector) {
        return page.locator(cssSelector).first().inputValue();
    }

    @Override
    public String getAttribute(String cssSelector, String attributeName) {
        return page.locator(cssSelector).first().getAttribute(attributeName);
    }

    @Override
    public boolean isVisible(String cssSelector) {
        return page.locator(cssSelector).first().isVisible();
    }

    @Override
    public boolean isEnabled(String cssSelector) {
        return page.locator(cssSelector).first().isEnabled();
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
    public void clearSession() {
        context.clearCookies();
        try {
            page.evaluate("() => { localStorage.clear(); sessionStorage.clear(); }");
        } catch (PlaywrightException ignored) {
        }
    }

    @Override
    public String captureScreenshotAsBase64() {
        byte[] bytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public void quit() {
        try {
            context.close();
        } finally {
            try {
                if (browser != null && userDataDir == null) {
                    browser.close();
                }
            } finally {
                try {
                    playwright.close();
                } finally {
                    deleteRecursivelyQuietly(userDataDir);
                }
            }
        }
    }

    private static BrowserType.LaunchOptions createLaunchOptions(UIWindowConfiguration windowConfiguration, boolean headless) {
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);
        if (windowConfiguration.isFullscreen() && !headless) {
            options.setArgs(Collections.singletonList("--start-maximized"));
        }
        return options;
    }

    private static Browser.NewContextOptions createContextOptions(UIWindowConfiguration windowConfiguration, boolean headless) {
        Browser.NewContextOptions options = new Browser.NewContextOptions();
        if (windowConfiguration.isFullscreen() && !headless) {
            options.setViewportSize(null);
        }
        return options;
    }

    private static BrowserType.LaunchPersistentContextOptions createPersistentContextOptions(
            UIWindowConfiguration windowConfiguration,
            boolean headless
    ) {
        BrowserType.LaunchPersistentContextOptions options = new BrowserType.LaunchPersistentContextOptions()
                .setHeadless(headless);
        if (windowConfiguration.isFullscreen() && !headless) {
            options.setArgs(Collections.singletonList("--start-maximized"));
            options.setViewportSize(null);
        }
        return options;
    }

    private void applyWindowConfiguration(UIWindowConfiguration windowConfiguration, boolean headless) {
        if (windowConfiguration.isFullscreen() && !headless) {
            return;
        }
        setWindowSize(windowConfiguration.width(), windowConfiguration.height());
    }

    private static Page acquireStartupPage(BrowserContext context) {
        List<Page> pages = context.pages();
        if (pages.isEmpty()) {
            return context.newPage();
        }

        Page primaryPage = pages.get(0);
        closeExtraPages(pages);
        return primaryPage;
    }

    private void ensureBrowserZoomConfigured() {
        if (browserZoomConfigured || windowConfiguration.zoomPercent() == UIWindowConfiguration.DEFAULT_ZOOM_PERCENT) {
            return;
        }

        page.navigate("chrome://settings/appearance");
        Object hasSettingsPrivate = page.evaluate(
                "() => typeof chrome !== 'undefined' && !!chrome.settingsPrivate"
        );
        if (!Boolean.TRUE.equals(hasSettingsPrivate)) {
            throw new PlaywrightException("chrome.settingsPrivate is not available for browser zoom configuration");
        }

        double expectedZoomFactor = windowConfiguration.zoomPercent() / 100.0d;
        page.evaluate("zoom => chrome.settingsPrivate.setDefaultZoom(zoom)", expectedZoomFactor);
        Object configuredZoom = page.evaluate(
                "() => new Promise(resolve => chrome.settingsPrivate.getDefaultZoom(resolve))"
        );
        if (!(configuredZoom instanceof Number)
                || Math.abs(((Number) configuredZoom).doubleValue() - expectedZoomFactor) > 0.01d) {
            throw new PlaywrightException("Browser zoom was not applied correctly");
        }

        browserZoomConfigured = true;
    }

    private boolean usesHeadlessZoomFallback() {
        return headless && windowConfiguration.zoomPercent() != UIWindowConfiguration.DEFAULT_ZOOM_PERCENT;
    }

    private void applyHeadlessPageZoomFallback() {
        page.evaluate(
                "zoomPercent => { document.documentElement.style.zoom = zoomPercent + '%'; }",
                windowConfiguration.zoomPercent()
        );
    }

    private static boolean requiresPersistentContextForZoom(UIWindowConfiguration windowConfiguration) {
        return windowConfiguration.zoomPercent() != UIWindowConfiguration.DEFAULT_ZOOM_PERCENT;
    }

    private void assertSingleStartupTarget(String phase) {
        int pageCount = context.pages().size();
        logStartupDebug("Playwright pages " + phase + ": " + pageCount);
        if (pageCount != 1) {
            throw new IllegalStateException("Expected exactly one Playwright page " + phase + " but found " + pageCount);
        }
    }

    private static void closeExtraPages(List<Page> pages) {
        for (int i = 1; i < pages.size(); i++) {
            try {
                pages.get(i).close();
            } catch (PlaywrightException ignored) {
            }
        }
    }

    private static Path createTemporaryUserDataDir() {
        try {
            return Files.createTempDirectory("playwright-zoom-profile-");
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create Playwright user data directory", e);
        }
    }

    private static void deleteRecursivelyQuietly(Path root) {
        if (root == null) {
            return;
        }

        try (Stream<Path> stream = Files.walk(root)) {
            stream.sorted((a, b) -> b.getNameCount() - a.getNameCount())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException ignored) {
                        }
                    });
        } catch (IOException ignored) {
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
