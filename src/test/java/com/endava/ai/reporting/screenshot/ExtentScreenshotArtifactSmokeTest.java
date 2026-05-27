package com.endava.ai.reporting.screenshot;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.listener.TestListener;
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
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Base64;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExtentScreenshotArtifactSmokeTest {

    private static final String SCREENSHOTS_ENABLED = "ui.screenshots.enabled";
    private static final String FAILURE_ONLY = "ui.screenshots.on.failure.only";
    private static final String CAPTURE_FINAL_STATE = "ui.screenshots.capture.final.state";
    private static final String UI_ENGINE = "ui.engine";
    private static final String REPORTING_ENGINE = "reporting.engine";
    private static final String REPORTS_DIR = "reports.dir";
    private static final String TIMESTAMP_ENABLED = "reports.timestamp.enabled";

    private String previousScreenshotsEnabled;
    private String previousFailureOnly;
    private String previousCaptureFinalState;
    private String previousUiEngine;
    private String previousReportingEngine;
    private String previousReportsDir;
    private String previousTimestampEnabled;

    @AfterMethod
    public void tearDown() {
        restoreConfig(SCREENSHOTS_ENABLED, previousScreenshotsEnabled);
        restoreConfig(FAILURE_ONLY, previousFailureOnly);
        restoreConfig(CAPTURE_FINAL_STATE, previousCaptureFinalState);
        restoreConfig(UI_ENGINE, previousUiEngine);
        restoreConfig(REPORTING_ENGINE, previousReportingEngine);
        restoreConfig(REPORTS_DIR, previousReportsDir);
        restoreConfig(TIMESTAMP_ENABLED, previousTimestampEnabled);

        setEngineForCurrentThread(null);
        FailureAttachmentRegistry.clearForTests();
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();
    }

    @Test
    public void extent_receives_final_and_failure_screenshots_without_duplicates_for_both_ui_engines() throws IOException {
        snapshotConfig();

        Path reportsDir = Path.of("target", "extent-final-screenshot-smoke").toAbsolutePath();
        deleteRecursively(reportsDir);
        Files.createDirectories(reportsDir);

        ConfigManager.set(REPORTING_ENGINE, "extent");
        ConfigManager.set(SCREENSHOTS_ENABLED, "true");
        ConfigManager.set(FAILURE_ONLY, "false");
        ConfigManager.set(CAPTURE_FINAL_STATE, "true");
        ConfigManager.set(REPORTS_DIR, reportsDir.toString());
        ConfigManager.set(TIMESTAMP_ENABLED, "false");

        FailureAttachmentRegistry.clearForTests();
        FailureAttachmentRegistry.register(new UiScreenshotFailureHandler());
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();

        for (String uiEngine : new String[]{"selenium", "playwright"}) {
            ConfigManager.set(UI_ENGINE, uiEngine);
            runSuccessfulUiFlow(uiEngine);
            runFailingUiFlow(uiEngine);
        }

        Path reportFile = findExtentReportFile(reportsDir, "extentSuccess_selenium");
        Assert.assertNotNull(reportFile, "Extent report file must exist");

        String content = Files.readString(reportFile);
        Assert.assertTrue(content.contains("Final Screenshot"));
        Assert.assertTrue(content.contains("Failure Screenshot"));
        Assert.assertTrue(content.contains("extentSuccess_selenium"));
        Assert.assertTrue(content.contains("extentSuccess_playwright"));
    }

    private void runSuccessfulUiFlow(String uiEngine) {
        TestListener listener = new TestListener();
        ITestResult result = successResult("extentSuccess_" + uiEngine);

        setEngineForCurrentThread(new FakeUiEngine("extent-success-" + uiEngine));
        listener.onTestStart(result);

        StepLogger.startStep("success");
        StepLogger.logDetail("ui success");
        StepLogger.pass("ok");

        listener.onTestSuccess(result);
        listener.onFinish((org.testng.ISuite) null);
        setEngineForCurrentThread(null);
    }

    private void runFailingUiFlow(String uiEngine) {
        TestListener listener = new TestListener();
        ITestResult result = failureResult("extentFailure_" + uiEngine, new AssertionError("expected failure"));

        setEngineForCurrentThread(new FakeUiEngine("extent-failure-" + uiEngine));
        listener.onTestStart(result);
        listener.onTestFailure(result);
        listener.onFinish((org.testng.ISuite) null);
        setEngineForCurrentThread(null);
    }

    private void snapshotConfig() {
        previousScreenshotsEnabled = ConfigManager.get(SCREENSHOTS_ENABLED, null);
        previousFailureOnly = ConfigManager.get(FAILURE_ONLY, null);
        previousCaptureFinalState = ConfigManager.get(CAPTURE_FINAL_STATE, null);
        previousUiEngine = ConfigManager.get(UI_ENGINE, null);
        previousReportingEngine = ConfigManager.get(REPORTING_ENGINE, null);
        previousReportsDir = ConfigManager.get(REPORTS_DIR, null);
        previousTimestampEnabled = ConfigManager.get(TIMESTAMP_ENABLED, null);
    }

    private static void restoreConfig(String key, String value) {
        if (value == null) {
            ConfigManager.clear(key);
        } else {
            ConfigManager.set(key, value);
        }
    }

    private static void deleteRecursively(Path root) throws IOException {
        if (!Files.exists(root)) return;

        try (Stream<Path> stream = Files.walk(root)) {
            stream.sorted((a, b) -> b.getNameCount() - a.getNameCount())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            throw new IllegalStateException("Failed to delete " + path, e);
                        }
                    });
        }
    }

    private static Path findExtentReportFile(Path preferredRoot, String expectedMarker) throws IOException {
        Path fromPreferredRoot = findExtentReportFileUnder(preferredRoot, expectedMarker);
        if (fromPreferredRoot != null) {
            return fromPreferredRoot;
        }
        return findExtentReportFileUnder(Path.of("target"), expectedMarker);
    }

    private static Path findExtentReportFileUnder(Path root, String expectedMarker) throws IOException {
        if (!Files.exists(root)) {
            return null;
        }

        try (Stream<Path> stream = Files.walk(root)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("ExtentReport"))
                    .filter(path -> path.getFileName().toString().endsWith(".html"))
                    .sorted(Comparator.comparingLong(ExtentScreenshotArtifactSmokeTest::lastModified).reversed())
                    .filter(path -> fileContains(path, expectedMarker))
                    .findFirst()
                    .orElse(null);
        }
    }

    private static boolean fileContains(Path path, String expectedMarker) {
        try {
            return Files.readString(path).contains(expectedMarker);
        } catch (IOException e) {
            return false;
        }
    }

    private static long lastModified(Path path) {
        try {
            return Files.getLastModifiedTime(path).toMillis();
        } catch (IOException e) {
            return Long.MIN_VALUE;
        }
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

    private static ITestResult successResult(String methodName) {
        TestResult result = TestResult.newEmptyTestResult();
        result.setStatus(ITestResult.SUCCESS);
        result.setMethod(fakeMethod(methodName));
        return result;
    }

    private static ITestResult failureResult(String methodName, Throwable t) {
        TestResult result = TestResult.newEmptyTestResult();
        result.setStatus(ITestResult.FAILURE);
        result.setThrowable(t);
        result.setMethod(fakeMethod(methodName));
        return result;
    }

    private static ITestNGMethod fakeMethod(String methodName) {
        ITestNGMethod method = mock(ITestNGMethod.class);
        when(method.getMethodName()).thenReturn(methodName);
        when(method.getDescription()).thenReturn(methodName);
        when(method.getRealClass()).thenReturn(ExtentScreenshotArtifactSmokeTest.class);
        return method;
    }

    private static final class FakeUiEngine implements UIEngine {

        private final String payload;

        private FakeUiEngine(String payload) {
            this.payload = Base64.getEncoder().encodeToString(payload.getBytes());
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
        @Override public String captureScreenshotAsBase64() { return payload; }
        @Override public void quit() {}
        @Override public void setWindowSize(int width, int height) {}
    }
}
