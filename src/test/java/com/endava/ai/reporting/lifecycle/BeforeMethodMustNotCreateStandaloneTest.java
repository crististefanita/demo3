package com.endava.ai.reporting.lifecycle;

import com.endava.ai.core.TestListener;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.reporting.util.FakeTestResult;
import com.endava.ai.reporting.util.RecordingTestNamesLogger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BeforeMethodMustNotCreateStandaloneTest {

    @Test
    public void before_method_must_not_start_fallback_test() {

        RecordingTestNamesLogger logger = new RecordingTestNamesLogger();
        ReportingManager.reset();
        ReportingManager.setLoggerForTests(logger);

        StepLogger.clear();

        StepLogger.startStep("Validate: status=200");
        StepLogger.pass("Validated");

        TestListener listener = new TestListener();
        listener.onTestStart(FakeTestResult.success());

        Assert.assertFalse(
                logger.startedTests.contains("Test"),
                "Fallback Extent test was started for @BeforeMethod steps"
        );
    }

    @Test
    public void before_method_between_two_tests_must_not_create_fallback_test() {
        RecordingTestNamesLogger logger = new RecordingTestNamesLogger();
        ReportingManager.reset();
        ReportingManager.setLoggerForTests(logger);

        TestListener listener = new TestListener();

        listener.onTestStart(FakeTestResult.success());
        listener.onTestSuccess(FakeTestResult.success());

        StepLogger.startStep("Validate: status=200");
        StepLogger.pass("Validated");

        listener.onTestStart(FakeTestResult.success());

        Assert.assertFalse(
                logger.startedTests.contains("Test"),
                "Fallback Extent test was started for @BeforeMethod between tests"
        );
    }
}
