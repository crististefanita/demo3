package com.endava.ai.core;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.ui.core.DriverManager;
import org.testng.*;

import java.util.concurrent.atomic.AtomicBoolean;

public final class TestListener implements ITestListener, ISuiteListener {

    private final ThreadLocal<AtomicBoolean> screenshotTaken =
            ThreadLocal.withInitial(() -> new AtomicBoolean(false));

    private boolean isAllure() {
        return !"allure".equalsIgnoreCase(
                ConfigManager.get("reporting.engine", "extent")
        );
    }

    @Override
    public void onStart(ISuite suite) {
        if (isAllure()) {
            ReportingManager.getLogger();
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        if (isAllure()) {
            ReportingManager.getLogger().flush();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        if (isAllure()) {
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
        if (isAllure()) {
            ReportingManager.getLogger().endTest("PASS");
        }
        StepLogger.clearTestStarted();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        if (isAllure()) {
            ReportingManager.getLogger().endTest("SKIP");
        }
        StepLogger.clearTestStarted();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            if (!screenshotTaken.get().getAndSet(true)
                    && DriverManager.hasActiveEngine()) {

                String base64 =
                        DriverManager.getEngine()
                                .captureScreenshotAsBase64();

                ReportingManager.getLogger()
                        .attachScreenshotBase64(base64, "Failure Screenshot");
            }
        } catch (Throwable ignored) {
            // listener must never throw
        } finally {
            if (isAllure()) {
                ReportingManager.getLogger().endTest("FAIL");
            }
            StepLogger.clearTestStarted();
        }
    }

    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult r) {}
    @Override public void onTestFailedWithTimeout(ITestResult r) {
        onTestFailure(r);
    }
}
