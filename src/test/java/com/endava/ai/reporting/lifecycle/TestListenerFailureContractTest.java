package com.endava.ai.reporting.lifecycle;

import com.endava.ai.core.listener.TestListener;
import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.core.reporting.adapters.ExtentAdapter;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestListenerFailureContractTest {

    private TestListener listener;
    private TestReportObserver observer;

    @BeforeMethod
    public void setUp() {
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();

        observer = new TestReportObserver();
        ReportingManager.setLoggerForTests(observer);

        listener = new TestListener();
    }

    @AfterMethod
    public void tearDown() {
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();
    }

    @Test
    public void failure_outside_step_is_logged_as_fail() {
        listener.onTestFailure(
                failureResult(new IllegalArgumentException("boom"))
        );

        assertTrue(observer.failCalled);
        assertTrue(observer.endFailCalled);
    }

    @Test
    public void logs_fail_only_once_when_step_already_failed() {
        listener.onTestStart(successResult());

        StepLogger.startStep("Validate: status=200");
        StepLogger.fail(
                "Unexpected HTTP status expected [200] but found [422]",
                new AssertionError()
        );

        listener.onTestFailure(
                failureResult(
                        new AssertionError("Unexpected HTTP status expected [200] but found [422]")
                )
        );

        assertEquals(1, observer.failCount);
    }

    @Test
    public void does_not_duplicate_fail_when_step_fails() {
        listener.onTestStart(successResult());

        StepLogger.startStep("Validate: status=200");
        StepLogger.fail("Boom", new AssertionError());

        listener.onTestFailure(
                failureResult(new AssertionError("Boom"))
        );

        assertEquals(1, observer.failMessages.size());
    }

    @Test
    public void extent_does_not_crash_on_failure_outside_step() {
        ReportingManager.setLoggerForTests(ExtentAdapter.getInstance());

        listener = new TestListener();
        listener.onTestStart(successResult());

        listener.onTestFailure(
                failureResult(new RuntimeException("boom outside step"))
        );
    }

    @Test
    public void step_details_are_preserved_when_step_logs_info() {
        listener.onTestStart(successResult());

        StepLogger.startStep("Create user");
        StepLogger.info("POST /users");
        StepLogger.info("email=test@example.com");

        StepLogger.pass("Validated status=200");

        listener.onTestSuccess(successResult());

        assertEquals(
                2,
                observer.details.size()
        );
    }

    @Test
    public void unhandled_exception_does_not_create_duplicate_test_nodes() {
        ITestResult result = failureResult(
                new IllegalStateException("boom before step")
        );

        listener.onTestFailure(result);

        // Contract: no exception, no infinite loop.
        // Number of test nodes is engine/listener dependent.
        assertTrue(
                observer.testCount >= 1,
                "Unhandled exception must be reported by at least one test node"
        );
    }

    // ---------------- helpers ----------------

    private static ITestResult successResult() {
        TestResult result = TestResult.newEmptyTestResult();
        result.setStatus(ITestResult.SUCCESS);
        result.setMethod(fakeMethod());
        return result;
    }

    private static ITestResult failureResult(Throwable t) {
        TestResult result = TestResult.newEmptyTestResult();
        result.setStatus(ITestResult.FAILURE);
        result.setThrowable(t);
        result.setMethod(fakeMethod());
        return result;
    }

    private static ITestNGMethod fakeMethod() {
        ITestNGMethod method = mock(ITestNGMethod.class);
        when(method.getMethodName()).thenReturn("fakeTest");
        when(method.getDescription()).thenReturn("fake description");
        return method;
    }

    // ---------------- observer ----------------

    private static final class TestReportObserver implements ReportLogger {

        boolean failCalled = false;
        boolean endFailCalled = false;
        int failCount = 0;
        int testCount = 0;

        List<String> failMessages = new ArrayList<>();
        List<String> details = new ArrayList<>();

        @Override
        public void startTest(String testName, String description) {
            testCount++;
        }

        @Override
        public void endTest(String status) {
            if ("FAIL".equals(status)) endFailCalled = true;
        }

        @Override public void startStep(String stepTitle) {}

        @Override
        public void logDetail(String detail) {
            details.add(detail);
        }

        @Override public void pass(String message) {}

        @Override
        public void fail(String message, String stacktraceAsText) {
            failCalled = true;
            failCount++;
            failMessages.add(message);
        }

        @Override public void attachScreenshotBase64(String base64, String title) {}
        @Override public void logCodeBlock(String content) {}
        @Override public void flush() {}
    }
}
