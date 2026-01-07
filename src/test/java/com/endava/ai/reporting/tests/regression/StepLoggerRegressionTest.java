package com.endava.ai.reporting.tests.regression;

import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.reporting.util.RecordingStepCallsLogger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class StepLoggerRegressionTest {

    @AfterMethod
    public void cleanup() {
        StepLogger.clear();
    }

    @Test
    public void happy_path_single_step_passes() {
        RecordingStepCallsLogger logger = new RecordingStepCallsLogger();
        StepLogger.setDelegate(logger);

        StepLogger.startStep("A");
        StepLogger.logDetail("x");
        StepLogger.pass("ok");

        assertEquals(List.of("A"), logger.steps);
        assertEquals(List.of("ok"), logger.passed);
        assertTrue(logger.failed.isEmpty());
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void cannot_start_nested_steps() {
        StepLogger.startStep("A");
        StepLogger.startStep("B");
    }

    @Test
    public void unhandled_exception_outside_step_is_reported_once() {
        RecordingStepCallsLogger logger = new RecordingStepCallsLogger();
        StepLogger.setDelegate(logger);

        StepLogger.failUnhandledOutsideStep(new RuntimeException("boom"));

        assertEquals(1, logger.failed.size());
    }
}
