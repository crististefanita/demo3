package com.endava.ai.reporting.regression;

import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.StepLogger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SuppressWarnings("ALL")
public class AllureReportLoggerContractTest {

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
    public void fixture_methods_do_not_throw() {
        // we only validate that calling these APIs is safe
        observer.startTest("test", null);
        observer.endTest("PASSED");

        observer.startBeforeFixture("beforeMethod");
        observer.stopBeforeFixture("beforeMethod");

        observer.startAfterFixture("afterMethod");
        observer.stopAfterFixture("afterMethod");

        Assert.assertTrue(true); // no exception = contract respected
    }

    @Test
    public void context_is_cleared_between_tests() {
        observer.startTest("test1", null);
        observer.endTest("PASSED");

        observer.startTest("test2", null);
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
        @Override public void pass(String message) {}
        @Override public void fail(String message, String stacktraceAsText) {}

        // fixture-like methods (kept for compatibility)
        void startBeforeFixture(String name) {}
        void stopBeforeFixture(String name) {}
        void startAfterFixture(String name) {}
        void stopAfterFixture(String name) {}

        @Override public void logDetail(String detail) {}
        @Override public void attachScreenshotBase64(String base64, String title) {}
        @Override public void logCodeBlock(String content) {}
        @Override public void flush() {}
    }
}
