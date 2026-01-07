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

    private TestListener listener;
    private RecordingReportingLogger recordingLogger;
    private FakeReportingLogger fakeLogger;

    @BeforeMethod
    public void setUp() {
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();
        listener = null;
        recordingLogger = null;
        fakeLogger = null;
    }

    @AfterMethod
    public void tearDown() {
        ReportingManager.reset();
        StepLogger.clear();
        TestListener.resetForTests();
    }

    @Test
    public void failure_outside_step_is_logged_as_fail() {
        fakeLogger = new FakeReportingLogger();
        init(fakeLogger);

        listener.onTestFailure(
                FakeTestResult.failure(new IllegalArgumentException("boom"))
        );

        assertTrue(fakeLogger.failCalled);
        assertTrue(fakeLogger.endFailCalled);
    }

    @Test
    public void logs_fail_only_once_when_step_already_failed() {
        fakeLogger = new FakeReportingLogger();
        init(fakeLogger);

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

        assertEquals(1, fakeLogger.failCount);
    }

    @Test
    public void does_not_duplicate_fail_message_when_step_fails() {
        recordingLogger = new RecordingReportingLogger();
        init(recordingLogger);

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

        assertEquals(0, recordingLogger.infoMessages.size());
        assertEquals(1, recordingLogger.failMessages.size());
    }

    @Test
    public void extent_marks_test_as_passed_even_if_failure_is_called_without_step() {
        ReportingManager.setLoggerForTests(ExtentAdapter.getInstance());

        listener = new TestListener();
        listener.onTestStart(FakeTestResult.success());

        listener.onTestFailure(
                FakeTestResult.failure(new RuntimeException("boom outside step"))
        );
    }

    @Test
    public void keeps_step_if_it_logged_info_even_if_pass_has_no_message() {
        recordingLogger = new RecordingReportingLogger();
        init(recordingLogger);

        listener.onTestStart(FakeTestResult.success());

        StepLogger.startStep("Create user");
        StepLogger.info("POST /users");
        StepLogger.info("email=test@example.com");

        StepLogger.pass("Validated status=200");

        listener.onTestSuccess(FakeTestResult.success());

        assertEquals(
                2,
                recordingLogger.detailsPerTest
                        .values()
                        .iterator()
                        .next()
                        .size()
        );
    }

    @Test
    public void unhandled_exception_outside_step_must_not_create_additional_reported_test() {
        recordingLogger = new RecordingReportingLogger();
        init(recordingLogger);

        ITestResult result = FakeTestResult.failure(
                new IllegalStateException("boom before step")
        );

        listener.onTestFailure(result);

        assertEquals(
                1,
                recordingLogger.detailsPerTest.size()
        );
    }

    private void init(FakeReportingLogger logger) {
        ReportingManager.setLoggerForTests(logger);
        listener = new TestListener();
    }

    private void init(RecordingReportingLogger logger) {
        ReportingManager.setLoggerForTests(logger);
        listener = new TestListener();
    }
}
