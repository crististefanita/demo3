package com.endava.ai.api.validation;

import com.endava.ai.ui.reporting.StepLogger;
import io.restassured.response.Response;
import org.testng.Assert;

public final class ResponseValidator {
    private ResponseValidator() {}

    public static void statusIs(Response resp, int expected) {
        StepLogger.startStep("Validate status: expect " + expected);
        try {
            int actual = resp.getStatusCode();
            StepLogger.logDetail("actual=" + actual);
            Assert.assertEquals(actual, expected, "Unexpected HTTP status");
            StepLogger.pass("Validated");
        } catch (AssertionError e) {
            StepLogger.fail(e.getMessage(), e);
            throw e;
        }
    }
}
