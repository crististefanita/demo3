package com.endava.ai.reporting.tests;

import com.endava.ai.core.listener.TestListener;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.core.reporting.adapters.ExtentAdapter;
import com.endava.ai.reporting.util.FakeReportingLogger;
import com.endava.ai.reporting.util.FakeTestResult;
import com.endava.ai.reporting.util.RecordingReportingLogger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class TestListenerFailureTest {
    @BeforeMethod
    public void resetStateBefore() {
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();
    }

    @AfterMethod
    public void resetStateAfter() {
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();
    }

    @Test
    public void test_failure_outside_step_is_logged_as_fail() {
        FakeReportingLogger logger = new FakeReportingLogger();
        ReportingManager.setLoggerForTests(logger);

        TestListener listener = new TestListener();

        ITestResult result = FakeTestResult.failure(
                new IllegalArgumentException("boom")
        );

        listener.onTestFailure(result);

        assertTrue(logger.failCalled);
        assertTrue(logger.endFailCalled);

        ReportingManager.reset();
    }

    @Test
    public void should_log_fail_only_once_when_step_already_failed() {
        ReportingManager.reset();

        FakeReportingLogger logger = new FakeReportingLogger();
        ReportingManager.setLoggerForTests(logger);

        TestListener listener = new TestListener();

        listener.onTestStart(FakeTestResult.success());

        StepLogger.startStep("Validate: status=200");
        StepLogger.fail(
                "Unexpected HTTP status expected [200] but found [422]",
                new AssertionError()
        );

        listener.onTestFailure(
                FakeTestResult.failure(
                        new AssertionError("Unexpected HTTP status expected [200] but found [422]")
                )
        );

        assertEquals(1, logger.failCount);

        ReportingManager.reset();
    }

    @Test
    public void should_not_duplicate_fail_message_when_step_fails() {
        ReportingManager.reset();

        RecordingReportingLogger logger = new RecordingReportingLogger();
        ReportingManager.setLoggerForTests(logger);

        TestListener listener = new TestListener();
        listener.onTestStart(FakeTestResult.success());

        StepLogger.startStep("Validate: status=200");
        StepLogger.fail(
                "Unexpected HTTP status expected [200] but found [422]",
                new AssertionError()
        );

        listener.onTestFailure(
                FakeTestResult.failure(
                        new AssertionError("Unexpected HTTP status expected [200] but found [422]")
                )
        );

        assertEquals(0, logger.infoMessages.size());
        assertEquals(1, logger.failMessages.size());

        ReportingManager.reset();
    }

    @Test
    public void extent_marks_test_as_passed_even_if_onTestFailure_is_called_without_step() {
        ReportingManager.reset();
        ReportingManager.setLoggerForTests(ExtentAdapter.getInstance());

        TestListener listener = new TestListener();

        listener.onTestStart(FakeTestResult.success());

        listener.onTestFailure(
                FakeTestResult.failure(
                        new RuntimeException("boom outside step")
                )
        );

        ReportingManager.reset();
    }
}
