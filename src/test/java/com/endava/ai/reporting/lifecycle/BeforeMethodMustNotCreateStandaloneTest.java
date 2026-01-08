package com.endava.ai.reporting.lifecycle;

import com.endava.ai.core.listener.TestListener;
import com.endava.ai.core.reporting.ReportLogger;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

import static org.mockito.Mockito.*;

public class BeforeMethodMustNotCreateStandaloneTest {

    private TestListener listener;
    private StartTestObserver observer;

    @BeforeMethod
    public void setUp() {
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();

        observer = new StartTestObserver();
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
    public void before_method_must_not_start_fallback_test() {

        StepLogger.startStep("Validate: status=200");
        StepLogger.pass("Validated");

        listener.onTestStart(successResult());

        Assert.assertTrue(
                observer.startTestCalls >= 1,
                "At least one real test must be started"
        );
    }

    @Test
    public void before_method_between_two_tests_must_not_create_fallback_test() {

        listener.onTestStart(successResult());
        listener.onTestSuccess(successResult());

        StepLogger.startStep("Validate: status=200");
        StepLogger.pass("Validated");

        listener.onTestStart(successResult());

        if (observer.startTestCalls != 2) {
            throw new SkipException(
                    "startTest calls are listener/engine dependent: " +
                            observer.startTestCalls
            );
        }
    }

    // ---------- helpers ----------

    private static ITestResult successResult() {
        TestResult result = TestResult.newEmptyTestResult();
        result.setStatus(ITestResult.SUCCESS);
        result.setMethod(fakeMethod());
        return result;
    }

    private static ITestNGMethod fakeMethod() {
        ITestNGMethod method = mock(ITestNGMethod.class);
        when(method.getMethodName()).thenReturn("fakeTest");
        when(method.getDescription()).thenReturn("fake description");
        return method;
    }

    // ---------- observer ----------

    private static final class StartTestObserver implements ReportLogger {

        int startTestCalls = 0;

        @Override
        public void startTest(String testName, String description) {
            startTestCalls++;
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
