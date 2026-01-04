package com.endava.ai.reporting;

import com.endava.ai.api.client.ApiActions;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import com.endava.ai.core.reporting.adapters.ExtentAdapter;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BeforeTestLifecycleFailureTest {

    @Test
    public void api_actions_can_be_called_before_test_start_and_are_logged() {

        ReportingManager.reset();
        ReportingManager.setLoggerForTests(ExtentAdapter.getInstance());

        StepLogger.clearTestStarted();

        try {
            ApiActions.execute(
                    "POST",
                    "/users",
                    "/users",
                    "{}",
                    () -> {
                        throw new RuntimeException("boom");
                    }
            );
            Assert.fail("Exception should be propagated");
        } catch (RuntimeException e) {
            Assert.assertEquals(e.getMessage(), "boom");
        }
    }
}
