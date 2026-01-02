package com.endava.ai.core;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.ui.core.DriverManager;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

public final class TestListener implements ITestListener, ISuiteListener {

    static {
        Runtime.getRuntime().addShutdownHook(
                new Thread(TestListener::deleteAllureResults)
        );
    }

    private final ThreadLocal<AtomicBoolean> screenshotTaken =
            ThreadLocal.withInitial(() -> new AtomicBoolean(false));

    private boolean useExtent() {
        return !"allure".equalsIgnoreCase(
                ConfigManager.get("reporting.engine", "extent")
        );
    }

    @Override
    public void onStart(ISuite suite) {
        if (useExtent()) {
            ReportingManager.getLogger();
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        if (useExtent()) {
            ReportingManager.getLogger().flush();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        if (useExtent()) {
            ReportingManager.getLogger().startTest(
                    result.getMethod().getMethodName(),
                    result.getMethod().getDescription()
            );
        }
        StepLogger.markTestStarted();
        screenshotTaken.get().set(false);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        endTest("PASS");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        endTest("SKIP");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            if (!StepLogger.testHasFailed()) {
                StepLogger.failUnhandledOutsideStep(result.getThrowable());
            }
            attachScreenshotOnce();
        } finally {
            endTest("FAIL");
        }
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    private void endTest(String status) {
        if (useExtent()) {
            ReportingManager.getLogger().endTest(status);
        }
        StepLogger.clearTestStarted();
    }

    private void attachScreenshotOnce() {
        if (!screenshotTaken.get().getAndSet(true) && DriverManager.hasActiveEngine()) {
            String base64 = DriverManager.getEngine().captureScreenshotAsBase64();
            ReportingManager.getLogger().attachScreenshotBase64(base64, "Failure Screenshot");
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void deleteAllureResults() {
        if ("allure".equalsIgnoreCase(
                ConfigManager.get("reporting.engine", "extent")
        )) return;

        File dir = new File("allure-results");
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File f : files) f.delete();
        dir.delete();
    }
}
