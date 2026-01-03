package com.endava.ai.api.validation;

import com.endava.ai.core.reporting.StepLogger;
import io.restassured.response.Response;
import org.testng.Assert;

import java.time.Duration;
import java.util.function.Supplier;

public final class ResponseValidator {

    private ResponseValidator() {
    }

    private static void validateStep(String stepName, Runnable assertion) {
        StepLogger.startStep(stepName);
        try {
            assertion.run();
            StepLogger.pass("Validated");
        } catch (AssertionError e) {
            StepLogger.fail(e.getMessage(), e);
            throw e;
        }
    }

    public static void statusIs(Response resp, int expected) {
        validateStep("Validate: status=" + expected, () -> {
            int actual = resp.getStatusCode();
            StepLogger.logDetail("actual=" + actual);
            Assert.assertEquals(actual, expected, "Unexpected HTTP status");
        });
    }

    public static void bodyContains(Response resp, String expectedText) {
        validateStep("Validate: body~=" + expectedText, () ->
                Assert.assertTrue(
                        resp.getBody().asString().contains(expectedText),
                        "Expected response body to contain: " + expectedText
                )
        );
    }

    public static Response waitForStatus(Supplier<Response> call, int expected) {
        Response resp = com.endava.ai.api.utils.WaitUtils.waitUntil(
                call,
                r -> r.getStatusCode() == expected,
                Duration.ofMillis(500)
        );
        statusIs(resp, expected);
        return resp;
    }
}
