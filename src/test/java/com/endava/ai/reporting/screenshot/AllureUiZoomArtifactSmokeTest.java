package com.endava.ai.reporting.screenshot;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.listener.TestListener;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.core.reporting.attachment.FailureAttachmentRegistry;
import com.endava.ai.ui.core.DriverManager;
import com.endava.ai.ui.engine.UIEngine;
import com.endava.ai.ui.engine.UIEngineFactory;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AllureUiZoomArtifactSmokeTest {

    private static final String SCREENSHOTS_ENABLED = "ui.screenshots.enabled";
    private static final String FAILURE_ONLY = "ui.screenshots.on.failure.only";
    private static final String CAPTURE_FINAL_STATE = "ui.screenshots.capture.final.state";
    private static final String UI_ENGINE = "ui.engine";
    private static final String UI_HEADLESS = "ui.headless";
    private static final String PAGE_ZOOM = "ui.page.zoom.percent";
    private static final String REPORTING_ENGINE = "reporting.engine";
    private static final String EXPLICIT_WAIT_SECONDS = "explicit.wait.seconds";

    private String previousScreenshotsEnabled;
    private String previousFailureOnly;
    private String previousCaptureFinalState;
    private String previousUiEngine;
    private String previousUiHeadless;
    private String previousPageZoom;
    private String previousReportingEngine;
    private String previousExplicitWaitSeconds;
    private String previousAllureDir;
    private AllureLifecycle previousLifecycle;

    @AfterMethod
    public void tearDown() {
        restoreConfig(SCREENSHOTS_ENABLED, previousScreenshotsEnabled);
        restoreConfig(FAILURE_ONLY, previousFailureOnly);
        restoreConfig(CAPTURE_FINAL_STATE, previousCaptureFinalState);
        restoreConfig(UI_ENGINE, previousUiEngine);
        restoreConfig(UI_HEADLESS, previousUiHeadless);
        restoreConfig(PAGE_ZOOM, previousPageZoom);
        restoreConfig(REPORTING_ENGINE, previousReportingEngine);
        restoreConfig(EXPLICIT_WAIT_SECONDS, previousExplicitWaitSeconds);

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
    public void allure_final_screenshots_reflect_real_zoom_difference(String uiEngine) throws Exception {
        snapshotConfig();

        Path baselineDir = prepareResultsDir("target/allure-zoom-visual-" + uiEngine + "-100");
        Path zoomedDir = prepareResultsDir("target/allure-zoom-visual-" + uiEngine + "-60");

        Path baselineScreenshot = runSuccessfulUiFlow(uiEngine, "100", baselineDir, "allureZoom100");
        Path zoomedScreenshot = runSuccessfulUiFlow(uiEngine, "60", zoomedDir, "allureZoom60");

        Assert.assertNotEquals(Files.size(baselineScreenshot), Files.size(zoomedScreenshot));
        Assert.assertTrue(
                imageDifferenceRatio(baselineScreenshot, zoomedScreenshot) > 0.01d,
                "Zoomed Allure screenshot should differ visibly for " + uiEngine
        );
    }

    private Path runSuccessfulUiFlow(String uiEngine, String zoomPercent, Path resultsDir, String methodName)
            throws Exception {
        ConfigManager.set(REPORTING_ENGINE, "allure");
        ConfigManager.set(UI_ENGINE, uiEngine);
        ConfigManager.set(UI_HEADLESS, "false");
        ConfigManager.set(SCREENSHOTS_ENABLED, "true");
        ConfigManager.set(FAILURE_ONLY, "false");
        ConfigManager.set(CAPTURE_FINAL_STATE, "true");
        ConfigManager.set(PAGE_ZOOM, zoomPercent);
        ConfigManager.set(EXPLICIT_WAIT_SECONDS, "10");
        System.setProperty("allure.results.directory", resultsDir.toString());
        Allure.setLifecycle(new AllureLifecycle(new FileSystemResultsWriter(resultsDir)));

        FailureAttachmentRegistry.clearForTests();
        FailureAttachmentRegistry.register(new UiScreenshotFailureHandler());
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();

        String uuid = UUID.randomUUID().toString();
        Allure.getLifecycle().scheduleTestCase(
                new io.qameta.allure.model.TestResult()
                        .setUuid(uuid)
                        .setName(methodName)
        );
        Allure.getLifecycle().startTestCase(uuid);

        TestListener listener = new TestListener();
        ITestResult result = successResult(methodName);
        UIEngine engine = UIEngineFactory.create();

        try {
            setEngineForCurrentThread(engine);
            listener.onTestStart(result);
            engine.open(ConfigManager.require("base.url") + "/auth/login");

            StepLogger.startStep("success");
            StepLogger.logDetail("ui success");
            StepLogger.pass("ok");

            listener.onTestSuccess(result);
            listener.onFinish((org.testng.ISuite) null);
        } finally {
            engine.quit();
            setEngineForCurrentThread(null);
        }

        Allure.getLifecycle().updateTestCase(uuid, testResult -> testResult.setStatus(Status.PASSED));
        Allure.getLifecycle().stopTestCase(uuid);
        Allure.getLifecycle().writeTestCase(uuid);

        return findOnlyPng(resultsDir);
    }

    private void snapshotConfig() {
        previousScreenshotsEnabled = ConfigManager.get(SCREENSHOTS_ENABLED, null);
        previousFailureOnly = ConfigManager.get(FAILURE_ONLY, null);
        previousCaptureFinalState = ConfigManager.get(CAPTURE_FINAL_STATE, null);
        previousUiEngine = ConfigManager.get(UI_ENGINE, null);
        previousUiHeadless = ConfigManager.get(UI_HEADLESS, null);
        previousPageZoom = ConfigManager.get(PAGE_ZOOM, null);
        previousReportingEngine = ConfigManager.get(REPORTING_ENGINE, null);
        previousExplicitWaitSeconds = ConfigManager.get(EXPLICIT_WAIT_SECONDS, null);
        previousAllureDir = System.getProperty("allure.results.directory");
        previousLifecycle = Allure.getLifecycle();
    }

    private static Path prepareResultsDir(String relativePath) throws IOException {
        Path resultsDir = Path.of(relativePath).toAbsolutePath();
        deleteRecursively(resultsDir);
        Files.createDirectories(resultsDir);
        return resultsDir;
    }

    private static Path findOnlyPng(Path root) throws IOException {
        try (Stream<Path> stream = Files.walk(root)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".png"))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No screenshot attachment found in " + root));
        }
    }

    private static double imageDifferenceRatio(Path first, Path second) throws IOException {
        BufferedImage left = ImageIO.read(first.toFile());
        BufferedImage right = ImageIO.read(second.toFile());
        if (left == null || right == null) {
            throw new IllegalStateException("Unable to decode screenshot artifacts");
        }

        int width = Math.min(left.getWidth(), right.getWidth());
        int height = Math.min(left.getHeight(), right.getHeight());
        long totalSamples = 0;
        long changedSamples = 0;

        for (int y = 0; y < height; y += 4) {
            for (int x = 0; x < width; x += 4) {
                totalSamples++;
                if (left.getRGB(x, y) != right.getRGB(x, y)) {
                    changedSamples++;
                }
            }
        }

        return totalSamples == 0 ? 0.0d : (double) changedSamples / totalSamples;
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
            stream.sorted(Comparator.comparingInt(Path::getNameCount).reversed())
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

    private static ITestNGMethod fakeMethod(String methodName) {
        ITestNGMethod method = mock(ITestNGMethod.class);
        when(method.getMethodName()).thenReturn(methodName);
        when(method.getDescription()).thenReturn(methodName);
        when(method.getRealClass()).thenReturn(AllureUiZoomArtifactSmokeTest.class);
        return method;
    }
}
