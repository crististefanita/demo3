package com.endava.ai.api.core;

import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public final class TestListener implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite suite) {
        // ensure reporting is initialized
        ReportingManager.getLogger();
    }

    @Override
    public void onFinish(ISuite suite) {
        ReportingManager.getLogger().flush();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ReportingManager.getLogger()
                .startTest(
                        result.getMethod().getMethodName(),
                        result.getMethod().getDescription()
                );
        StepLogger.markTestStarted();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ReportingManager.getLogger().endTest("PASS");
        StepLogger.clearTestStarted();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ReportingManager.getLogger().endTest("FAIL");
        StepLogger.clearTestStarted();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ReportingManager.getLogger().endTest("SKIP");
        StepLogger.clearTestStarted();
    }
}
