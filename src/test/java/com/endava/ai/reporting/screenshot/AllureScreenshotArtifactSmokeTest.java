package com.endava.ai.reporting.screenshot;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.listener.TestListener;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.core.reporting.attachment.FailureAttachmentRegistry;
import com.endava.ai.ui.core.DriverManager;
import com.endava.ai.ui.engine.UIEngine;
import com.endava.ai.ui.reporting.UiScreenshotFailureHandler;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.FileSystemResultsWriter;
import io.qameta.allure.model.Status;
import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AllureScreenshotArtifactSmokeTest {

    private static final String SCREENSHOTS_ENABLED = "ui.screenshots.enabled";
    private static final String FAILURE_ONLY = "ui.screenshots.on.failure.only";
    private static final String CAPTURE_FINAL_STATE = "ui.screenshots.capture.final.state";
    private static final String UI_ENGINE = "ui.engine";
    private static final String REPORTING_ENGINE = "reporting.engine";

    private String previousScreenshotsEnabled;
    private String previousFailureOnly;
    private String previousCaptureFinalState;
    private String previousUiEngine;
    private String previousReportingEngine;
    private String previousAllureDir;
    private AllureLifecycle previousLifecycle;

    @AfterMethod
    public void tearDown() {
        restoreConfig(SCREENSHOTS_ENABLED, previousScreenshotsEnabled);
        restoreConfig(FAILURE_ONLY, previousFailureOnly);
        restoreConfig(CAPTURE_FINAL_STATE, previousCaptureFinalState);
        restoreConfig(UI_ENGINE, previousUiEngine);
        restoreConfig(REPORTING_ENGINE, previousReportingEngine);

        if (previousAllureDir == null) {
            System.clearProperty("allure.results.directory");
        } else {
            System.setProperty("allure.results.directory", previousAllureDir);
        }
        if (previousLifecycle != null) {
            Allure.setLifecycle(previousLifecycle);
        }

        setEngineForCurrentThread(null);
        FailureAttachmentRegistry.clearForTests();
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();
    }

    @DataProvider
    public Object[][] uiEngines() {
        return new Object[][]{
                {"selenium"},
                {"playwright"}
        };
    }

    @Test(dataProvider = "uiEngines")
    public void allure_receives_final_and_failure_screenshots_without_duplicates(String uiEngine) throws IOException {
        snapshotConfig();

        Path resultsDir = Path.of("target", "allure-results-final-screenshot-smoke-" + uiEngine).toAbsolutePath();
        deleteRecursively(resultsDir);
        Files.createDirectories(resultsDir);

        ConfigManager.set(REPORTING_ENGINE, "allure");
        ConfigManager.set(UI_ENGINE, uiEngine);
        ConfigManager.set(SCREENSHOTS_ENABLED, "true");
        ConfigManager.set(FAILURE_ONLY, "false");
        ConfigManager.set(CAPTURE_FINAL_STATE, "true");
        System.setProperty("allure.results.directory", resultsDir.toString());
        previousLifecycle = Allure.getLifecycle();
        Allure.setLifecycle(new AllureLifecycle(new FileSystemResultsWriter(resultsDir)));

        FailureAttachmentRegistry.clearForTests();
        FailureAttachmentRegistry.register(new UiScreenshotFailureHandler());
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();

        runSuccessfulUiFlow(uiEngine);
        runFailingUiFlow(uiEngine);

        Assert.assertEquals(countOccurrences(resultsDir, "Final Screenshot"), 1);
        Assert.assertEquals(countOccurrences(resultsDir, "Failure Screenshot"), 1);
        Assert.assertTrue(countFiles(resultsDir, ".png") >= 2);
    }

    private void runSuccessfulUiFlow(String uiEngine) {
        String uuid = UUID.randomUUID().toString();
        Allure.getLifecycle().scheduleTestCase(
                new io.qameta.allure.model.TestResult()
                        .setUuid(uuid)
                        .setName("allure-success")
        );
        Allure.getLifecycle().startTestCase(uuid);

        TestListener listener = new TestListener();
        ITestResult result = successResult("allureSuccess");

        setEngineForCurrentThread(new FakeUiEngine("allure-success-" + uiEngine));
        listener.onTestStart(result);

        StepLogger.startStep("success");
        StepLogger.logDetail("ui success");
        StepLogger.pass("ok");

        listener.onTestSuccess(result);
        listener.onFinish((org.testng.ISuite) null);

        Allure.getLifecycle().updateTestCase(uuid, testResult -> testResult.setStatus(Status.PASSED));
        Allure.getLifecycle().stopTestCase(uuid);
        Allure.getLifecycle().writeTestCase(uuid);
        setEngineForCurrentThread(null);
    }

    private void runFailingUiFlow(String uiEngine) {
        String uuid = UUID.randomUUID().toString();
        Allure.getLifecycle().scheduleTestCase(
                new io.qameta.allure.model.TestResult()
                        .setUuid(uuid)
                        .setName("allure-failure")
        );
        Allure.getLifecycle().startTestCase(uuid);

        TestListener listener = new TestListener();
        ITestResult result = failureResult("allureFailure", new AssertionError("expected failure"));

        setEngineForCurrentThread(new FakeUiEngine("allure-failure-" + uiEngine));
        listener.onTestStart(result);
        listener.onTestFailure(result);
        listener.onFinish((org.testng.ISuite) null);

        Allure.getLifecycle().updateTestCase(uuid, testResult -> testResult.setStatus(Status.FAILED));
        Allure.getLifecycle().stopTestCase(uuid);
        Allure.getLifecycle().writeTestCase(uuid);
        setEngineForCurrentThread(null);
    }

    private void snapshotConfig() {
        previousScreenshotsEnabled = ConfigManager.get(SCREENSHOTS_ENABLED, null);
        previousFailureOnly = ConfigManager.get(FAILURE_ONLY, null);
        previousCaptureFinalState = ConfigManager.get(CAPTURE_FINAL_STATE, null);
        previousUiEngine = ConfigManager.get(UI_ENGINE, null);
        previousReportingEngine = ConfigManager.get(REPORTING_ENGINE, null);
        previousAllureDir = System.getProperty("allure.results.directory");
        previousLifecycle = null;
    }

    private static int countOccurrences(Path root, String needle) throws IOException {
        try (Stream<Path> stream = Files.walk(root)) {
            return stream
                    .filter(Files::isRegularFile)
                    .map(AllureScreenshotArtifactSmokeTest::readQuietly)
                    .mapToInt(content -> content.contains(needle) ? 1 : 0)
                    .sum();
        }
    }

    private static long countFiles(Path root, String suffix) throws IOException {
        try (Stream<Path> stream = Files.walk(root)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(suffix))
                    .count();
        }
    }

    private static String readQuietly(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException ignored) {
            return "";
        }
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
        when(method.getRealClass()).thenReturn(AllureScreenshotArtifactSmokeTest.class);
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
