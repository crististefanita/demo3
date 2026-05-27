package com.endava.ai.reporting.screenshot;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.listener.TestListener;
import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.core.reporting.attachment.FailureAttachmentRegistry;
import com.endava.ai.ui.core.DriverManager;
import com.endava.ai.ui.engine.UIEngine;
import com.endava.ai.ui.reporting.UiScreenshotFailureHandler;
import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FinalUiScreenshotContractTest {

    private static final String SCREENSHOTS_ENABLED = "ui.screenshots.enabled";
    private static final String FAILURE_ONLY = "ui.screenshots.on.failure.only";
    private static final String CAPTURE_FINAL_STATE = "ui.screenshots.capture.final.state";
    private static final String UI_ENGINE = "ui.engine";

    private final UiScreenshotFailureHandler handler = new UiScreenshotFailureHandler();

    private ScreenshotObserver observer;
    private TestListener listener;
    private String previousScreenshotsEnabled;
    private String previousFailureOnly;
    private String previousCaptureFinalState;
    private String previousUiEngine;
    private String previousReportingEngine;

    @BeforeMethod
    public void setUp() {
        previousScreenshotsEnabled = ConfigManager.get(SCREENSHOTS_ENABLED, null);
        previousFailureOnly = ConfigManager.get(FAILURE_ONLY, null);
        previousCaptureFinalState = ConfigManager.get(CAPTURE_FINAL_STATE, null);
        previousUiEngine = ConfigManager.get(UI_ENGINE, null);
        previousReportingEngine = ConfigManager.get("reporting.engine", null);

        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();
        FailureAttachmentRegistry.clearForTests();

        observer = new ScreenshotObserver();
        ReportingManager.setLoggerForTests(observer);
        FailureAttachmentRegistry.register(handler);
        listener = new TestListener();

        configureScreenshots("playwright", "extent", true, false, true);
        setEngineForCurrentThread(null);
    }

    @AfterMethod
    public void tearDown() {
        setEngineForCurrentThread(null);
        restoreConfig(SCREENSHOTS_ENABLED, previousScreenshotsEnabled);
        restoreConfig(FAILURE_ONLY, previousFailureOnly);
        restoreConfig(CAPTURE_FINAL_STATE, previousCaptureFinalState);
        restoreConfig(UI_ENGINE, previousUiEngine);
        restoreConfig("reporting.engine", previousReportingEngine);

        FailureAttachmentRegistry.clearForTests();
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();
    }

    @DataProvider
    public Object[][] screenshotMatrix() {
        return new Object[][]{
                {"selenium", "allure", false, true, false, false},
                {"selenium", "allure", true, false, false, false},
                {"selenium", "allure", true, true, false, true},
                {"selenium", "allure", true, true, true, false},
                {"selenium", "extent", false, true, false, false},
                {"selenium", "extent", true, false, false, false},
                {"selenium", "extent", true, true, false, true},
                {"selenium", "extent", true, true, true, false},
                {"playwright", "allure", false, true, false, false},
                {"playwright", "allure", true, false, false, false},
                {"playwright", "allure", true, true, false, true},
                {"playwright", "allure", true, true, true, false},
                {"playwright", "extent", false, true, false, false},
                {"playwright", "extent", true, false, false, false},
                {"playwright", "extent", true, true, false, true},
                {"playwright", "extent", true, true, true, false}
        };
    }

    @Test(dataProvider = "screenshotMatrix")
    public void successful_ui_tests_follow_final_screenshot_contract(
            String uiEngine,
            String reportingEngine,
            boolean enabled,
            boolean captureFinalState,
            boolean failureOnly,
            boolean expectFinalScreenshot
    ) {
        configureScreenshots(uiEngine, reportingEngine, enabled, failureOnly, captureFinalState);

        FakeUiEngine engine = new FakeUiEngine(uiEngine + "-success");
        setEngineForCurrentThread(engine);

        ITestResult result = successResult();
        listener.onTestStart(result);
        listener.onTestSuccess(result);

        assertTitles(expectFinalScreenshot ? List.of("Final Screenshot") : List.of());
        Assert.assertEquals(engine.captureCalls(), expectFinalScreenshot ? 1 : 0);
    }

    @Test(dataProvider = "screenshotMatrix")
    public void failed_ui_tests_preserve_existing_failure_screenshot_contract(
            String uiEngine,
            String reportingEngine,
            boolean enabled,
            boolean captureFinalState,
            boolean failureOnly,
            boolean expectFinalScreenshot
    ) {
        configureScreenshots(uiEngine, reportingEngine, enabled, failureOnly, captureFinalState);

        FakeUiEngine engine = new FakeUiEngine(uiEngine + "-failure");
        setEngineForCurrentThread(engine);

        ITestResult result = failureResult(new AssertionError("boom"));
        listener.onTestStart(result);
        listener.onTestFailure(result);

        assertTitles(enabled ? List.of("Failure Screenshot") : List.of());
        Assert.assertEquals(engine.captureCalls(), enabled ? 1 : 0);
    }

    @Test(dataProvider = "screenshotMatrix")
    public void non_ui_success_flow_never_attempts_ui_screenshots(
            String uiEngine,
            String reportingEngine,
            boolean enabled,
            boolean captureFinalState,
            boolean failureOnly,
            boolean expectFinalScreenshot
    ) {
        configureScreenshots(uiEngine, reportingEngine, enabled, failureOnly, captureFinalState);

        ITestResult result = successResult();
        listener.onTestStart(result);
        listener.onTestSuccess(result);

        assertTitles(List.of());
    }

    @Test(dataProvider = "screenshotMatrix")
    public void non_ui_failure_flow_never_attempts_ui_screenshots(
            String uiEngine,
            String reportingEngine,
            boolean enabled,
            boolean captureFinalState,
            boolean failureOnly,
            boolean expectFinalScreenshot
    ) {
        configureScreenshots(uiEngine, reportingEngine, enabled, failureOnly, captureFinalState);

        ITestResult result = failureResult(new AssertionError("boom"));
        listener.onTestStart(result);
        listener.onTestFailure(result);

        assertTitles(List.of());
    }

    @Test
    public void final_and_failure_capture_do_not_duplicate_screenshots() {
        FakeUiEngine engine = new FakeUiEngine("dedupe");
        setEngineForCurrentThread(engine);

        handler.onTestStart();
        handler.captureFinalStateIfEligible();
        handler.onTestFailure();

        Assert.assertEquals(observer.screenshotTitles(), List.of("Final Screenshot"));
        Assert.assertEquals(engine.captureCalls(), 1);
    }

    @Test
    public void final_capture_is_thread_safe_in_parallel_execution() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            Future<Integer> first = executor.submit(() -> runParallelCapture("parallel-1"));
            Future<Integer> second = executor.submit(() -> runParallelCapture("parallel-2"));

            Assert.assertEquals((int) first.get(10, TimeUnit.SECONDS), 1);
            Assert.assertEquals((int) second.get(10, TimeUnit.SECONDS), 1);
            Assert.assertEquals(observer.screenshotTitles().size(), 2);
            Assert.assertEquals(
                    countScreenshotsWithTitle("Final Screenshot"),
                    2
            );
        } finally {
            executor.shutdownNow();
        }
    }

    private int runParallelCapture(String name) {
        FakeUiEngine engine = new FakeUiEngine(name);
        setEngineForCurrentThread(engine);

        try {
            handler.onTestStart();
            handler.captureFinalStateIfEligible();
            return engine.captureCalls();
        } finally {
            setEngineForCurrentThread(null);
        }
    }

    private int countScreenshotsWithTitle(String title) {
        int count = 0;
        for (String value : observer.screenshotTitles()) {
            if (title.equals(value)) {
                count++;
            }
        }
        return count;
    }

    private void assertTitles(List<String> expected) {
        Assert.assertEquals(observer.screenshotTitles(), expected);
    }

    private void configureScreenshots(
            String uiEngine,
            String reportingEngine,
            boolean enabled,
            boolean failureOnly,
            boolean captureFinalState
    ) {
        ConfigManager.set(UI_ENGINE, uiEngine);
        ConfigManager.set("reporting.engine", reportingEngine);
        ConfigManager.set(SCREENSHOTS_ENABLED, Boolean.toString(enabled));
        ConfigManager.set(FAILURE_ONLY, Boolean.toString(failureOnly));
        ConfigManager.set(CAPTURE_FINAL_STATE, Boolean.toString(captureFinalState));
    }

    private void restoreConfig(String key, String value) {
        if (value == null) {
            ConfigManager.clear(key);
            return;
        }
        ConfigManager.set(key, value);
    }

    private static void setEngineForCurrentThread(UIEngine engine) {
        try {
            Field field = DriverManager.class.getDeclaredField("ENGINE");
            field.setAccessible(true);

            @SuppressWarnings("unchecked")
            ThreadLocal<UIEngine> engines = (ThreadLocal<UIEngine>) field.get(null);

            if (engine == null) {
                engines.remove();
            } else {
                engines.set(engine);
            }
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Unable to set UI engine for tests", e);
        }
    }

    private static ITestResult successResult() {
        TestResult result = TestResult.newEmptyTestResult();
        result.setStatus(ITestResult.SUCCESS);
        result.setMethod(fakeMethod());
        return result;
    }

    private static ITestResult failureResult(Throwable t) {
        TestResult result = TestResult.newEmptyTestResult();
        result.setStatus(ITestResult.FAILURE);
        result.setThrowable(t);
        result.setMethod(fakeMethod());
        return result;
    }

    private static ITestNGMethod fakeMethod() {
        ITestNGMethod method = mock(ITestNGMethod.class);
        when(method.getMethodName()).thenReturn("fakeUiTest");
        when(method.getDescription()).thenReturn("fake ui description");
        when(method.getRealClass()).thenReturn(FinalUiScreenshotContractTest.class);
        return method;
    }

    private static final class ScreenshotObserver implements ReportLogger {

        private final List<String> screenshotTitles = new CopyOnWriteArrayList<>();

        List<String> screenshotTitles() {
            return new ArrayList<>(screenshotTitles);
        }

        @Override public void startTest(String testName, String description) {}
        @Override public void endTest(String status) {}
        @Override public void startStep(String stepTitle) {}
        @Override public void logDetail(String detail) {}
        @Override public void pass(String message) {}
        @Override public void fail(String message, String stacktraceAsText) {}

        @Override
        public void attachScreenshotBase64(String base64, String title) {
            screenshotTitles.add(title);
        }

        @Override public void logCodeBlock(String content) {}
        @Override public void flush() {}
    }

    private static final class FakeUiEngine implements UIEngine {

        private final String screenshot;
        private final AtomicInteger captureCalls = new AtomicInteger();

        private FakeUiEngine(String screenshot) {
            this.screenshot = screenshot;
        }

        int captureCalls() {
            return captureCalls.get();
        }

        @Override public boolean supportsAutoWait() { return true; }
        @Override public void open(String url) {}
        @Override public void click(String cssSelector) {}
        @Override public void type(String cssSelector, String text) {}
        @Override public void select(String cssSelector, String valueOrText) {}
        @Override public String getText(String cssSelector) { return ""; }
        @Override public String getValue(String cssSelector) { return ""; }
        @Override public boolean isVisible(String cssSelector) { return true; }
        @Override public void waitForVisible(String cssSelector, int seconds) {}
        @Override public void waitForUrlContains(String fragment, int seconds) {}
        @Override public String getCurrentUrl() { return "about:blank"; }

        @Override
        public String captureScreenshotAsBase64() {
            captureCalls.incrementAndGet();
            return screenshot;
        }

        @Override public void quit() {}
        @Override public void setWindowSize(int width, int height) {}
    }
}
