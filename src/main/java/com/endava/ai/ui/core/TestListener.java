package com.endava.ai.ui.core;

import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import org.testng.*;

import java.util.concurrent.atomic.AtomicBoolean;

public final class TestListener implements ITestListener, ISuiteListener {

    private final ThreadLocal<AtomicBoolean> screenshotTaken =
            ThreadLocal.withInitial(() -> new AtomicBoolean(false));

    @Override
    public void onStart(ISuite suite) {
        ReportingManager.getLogger();
    }

    @Override
    public void onFinish(ISuite suite) {
        ReportingManager.getLogger().flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ReportingManager.getLogger().startTest(
                result.getMethod().getMethodName(),
                result.getMethod().getDescription()
        );
        StepLogger.markTestStarted();
        screenshotTaken.get().set(false);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ReportingManager.getLogger().endTest("PASS");
        StepLogger.clearTestStarted();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ReportingManager.getLogger().endTest("SKIP");
        StepLogger.clearTestStarted();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            // 🔑 SCREENSHOT ONLY IF UI ENGINE IS ACTIVE
            if (!screenshotTaken.get().getAndSet(true)
                    && DriverManager.hasActiveEngine()) {

                String base64 =
                        DriverManager.getEngine()
                                .captureScreenshotAsBase64();

                ReportingManager.getLogger()
                        .attachScreenshotBase64(base64, "Failure Screenshot");
            }
        } catch (Throwable ignored) {
            // Listener must never throw
        } finally {
            ReportingManager.getLogger().endTest("FAIL");
            StepLogger.clearTestStarted();
        }
    }

    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult r) {}
    @Override public void onTestFailedWithTimeout(ITestResult r) {
        onTestFailure(r);
    }
}
