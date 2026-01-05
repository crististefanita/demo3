package com.endava.ai.reporting.tests;

import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.core.reporting.adapters.ExtentAdapter;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BeforeTestLifecycleFailureTest {

    @Test
    public void steps_can_be_logged_before_test_start_and_exception_is_propagated() {

        ReportingManager.reset();
        ReportingManager.setLoggerForTests(ExtentAdapter.getInstance());
        StepLogger.clear();

        try {
            StepLogger.startStep("POST /users");
            StepLogger.logDetail("payload: {}");

            throw new RuntimeException("boom");
        } catch (RuntimeException e) {
            Assert.assertEquals(e.getMessage(), "boom");
        }
    }
}
