package com.endava.ai.reporting.regression;

import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.StepLogger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AllureFlowTest {

    private NoOpObserver observer;

    @BeforeMethod
    public void setUp() {
        observer = new NoOpObserver();
        StepLogger.clear();
        StepLogger.setDelegate(observer);
    }

    @AfterMethod
    public void tearDown() {
        StepLogger.clear();
    }

    @Test
    public void full_flow_does_not_throw_and_isolated() {

        // simulate a full test flow
        observer.startTest("test1", null);

        StepLogger.startStep("Step A");
        StepLogger.logDetail("detail A");
        StepLogger.pass("ok");

        observer.endTest("PASSED");

        observer.startTest("test2", null);

        StepLogger.startStep("Step B");
        StepLogger.logDetail("detail B");
        StepLogger.pass("ok");

        observer.endTest("PASSED");

        Assert.assertEquals(observer.startedTests, 2);
    }

    // ---------- observer ----------

    private static final class NoOpObserver implements ReportLogger {

        int startedTests = 0;

        @Override
        public void startTest(String testName, String description) {
            startedTests++;
        }

        @Override public void endTest(String status) {}
        @Override public void startStep(String stepTitle) {}
        @Override public void logDetail(String detail) {}
        @Override public void pass(String message) {}
        @Override public void fail(String message, String stacktraceAsText) {}
        @Override public void attachScreenshotBase64(String base64, String title) {}
        @Override public void logCodeBlock(String content) {}
        @Override public void flush() {}
    }
}
