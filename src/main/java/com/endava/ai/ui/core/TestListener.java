package com.endava.ai.ui.core;

import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import io.qameta.allure.Allure;
import org.testng.*;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Test lifecycle, reporting entry point (ONLY starter/ender of tests).
 * Compatible with Extent (via ReportingManager) and Allure (via TestNG listener).
 */
public final class TestListener implements ITestListener, ISuiteListener {

    private final ThreadLocal<AtomicBoolean> screenshotTaken =
            ThreadLocal.withInitial(() -> new AtomicBoolean(false));

    @Override
    public void onStart(ISuite suite) {
        // Initialize reporting early (Extent / others)
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
            if (!screenshotTaken.get().getAndSet(true)) {
                String base64 = DriverManager.getEngine().captureScreenshotAsBase64();

                // Extent (existing behavior)
                ReportingManager.getLogger()
                        .attachScreenshotBase64(base64, "Failure Screenshot");

                // Allure (FIX)
                byte[] bytes = Base64.getDecoder().decode(base64);
                Allure.addAttachment(
                        "Failure Screenshot",
                        "image/png",
                        new ByteArrayInputStream(bytes),
                        ".png"
                );
            }
        } catch (Throwable ignored) {
            // Listener must never throw
        } finally {
            ReportingManager.getLogger().endTest("FAIL");
            StepLogger.clearTestStarted();
        }
    }

    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onTestFailedWithTimeout(ITestResult result) { onTestFailure(result); }
}
