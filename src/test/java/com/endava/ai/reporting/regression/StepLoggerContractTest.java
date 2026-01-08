package com.endava.ai.reporting.regression;

import com.endava.ai.core.reporting.StepLogger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StepLoggerContractTest {

    @BeforeMethod
    public void setUp() {
        StepLogger.clear();
    }

    @AfterMethod
    public void tearDown() {
        StepLogger.clear();
    }

    @Test
    public void happy_path_single_step_does_not_throw() {
        StepLogger.startStep("A");
        StepLogger.logDetail("x");
        StepLogger.pass("ok");

        // contract: no exception
        Assert.assertTrue(true);
    }

    @Test
    public void nested_steps_are_allowed() {
        StepLogger.startStep("A");
        StepLogger.startStep("B");

        StepLogger.pass("ok B");
        StepLogger.pass("ok A");

        // contract: nesting allowed, no exception
        Assert.assertTrue(true);
    }

    @Test
    public void unhandled_exception_outside_step_is_idempotent() {
        StepLogger.failUnhandledOutsideStep(new RuntimeException("boom"));
        StepLogger.failUnhandledOutsideStep(new RuntimeException("boom again"));

        // contract: no exception, no infinite loop
        Assert.assertTrue(true);
    }
}
