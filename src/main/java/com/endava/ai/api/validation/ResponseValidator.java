package com.endava.ai.api.validation;

import com.endava.ai.api.model.ApiErrorMessage;
import com.endava.ai.api.model.ApiValidationError;
import com.endava.ai.core.reporting.StepLogger;
import io.restassured.response.Response;
import org.testng.Assert;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    public static void contentTypeContains(Response resp, String expectedFragment) {
        validateStep("Validate: content-type~=" + expectedFragment, () -> {
            String actual = resp.getContentType();
            StepLogger.logDetail("actual=" + actual);
            Assert.assertTrue(
                    actual != null && actual.contains(expectedFragment),
                    "Expected content type to contain: " + expectedFragment
            );
        });
    }

    public static void headerPresent(Response resp, String headerName) {
        validateStep("Validate: header present=" + headerName, () -> {
            String actual = resp.getHeader(headerName);
            StepLogger.logDetail("actual=" + actual);
            Assert.assertNotNull(actual, "Expected header to be present: " + headerName);
            Assert.assertFalse(actual.isBlank(), "Expected header to be non-blank: " + headerName);
        });
    }

    public static void headerEquals(Response resp, String headerName, String expectedValue) {
        validateStep("Validate: header " + headerName + "=" + expectedValue, () -> {
            String actual = resp.getHeader(headerName);
            StepLogger.logDetail("actual=" + actual);
            Assert.assertEquals(actual, expectedValue, "Unexpected header value for: " + headerName);
        });
    }

    public static void fieldEquals(Response resp, String jsonPath, Object expectedValue) {
        validateStep("Validate: " + jsonPath + "=" + expectedValue, () -> {
            Object actual = resp.jsonPath().get(jsonPath);
            StepLogger.logDetail("actual=" + actual);
            Assert.assertEquals(actual, expectedValue, "Unexpected value for path: " + jsonPath);
        });
    }

    public static void fieldNotBlank(Response resp, String jsonPath) {
        validateStep("Validate: " + jsonPath + " is non-blank", () -> {
            Object actual = resp.jsonPath().get(jsonPath);
            StepLogger.logDetail("actual=" + actual);
            Assert.assertNotNull(actual, "Expected non-null value for path: " + jsonPath);
            Assert.assertFalse(actual.toString().trim().isEmpty(), "Expected non-blank value for path: " + jsonPath);
        });
    }

    public static void messageEquals(Response resp, String expectedMessage) {
        validateStep("Validate: message=" + expectedMessage, () -> {
            ApiErrorMessage actual = errorMessage(resp);
            StepLogger.logDetail("actual=" + actual.message);
            Assert.assertEquals(actual.message, expectedMessage, "Unexpected error message");
        });
    }

    public static void arrayNotEmpty(Response resp) {
        validateStep("Validate: array not empty", () -> {
            List<?> items = resp.jsonPath().getList("$");
            int size = items == null ? 0 : items.size();
            StepLogger.logDetail("actual.size=" + size);
            Assert.assertTrue(items != null && !items.isEmpty(), "Expected response array to be non-empty");
        });
    }

    public static void arraySizeAtMost(Response resp, int maxSize) {
        validateStep("Validate: array size <= " + maxSize, () -> {
            List<?> items = resp.jsonPath().getList("$");
            int size = items == null ? 0 : items.size();
            StepLogger.logDetail("actual.size=" + size);
            Assert.assertTrue(
                    size <= maxSize,
                    "Expected response array size to be <= " + maxSize + " but was " + size
            );
        });
    }

    public static void arraySizeEquals(Response resp, int expectedSize) {
        validateStep("Validate: array size = " + expectedSize, () -> {
            List<?> items = resp.jsonPath().getList("$");
            int size = items == null ? 0 : items.size();
            StepLogger.logDetail("actual.size=" + size);
            Assert.assertEquals(size, expectedSize, "Unexpected response array size");
        });
    }

    public static void everyFieldEquals(Response resp, String jsonPath, Object expectedValue) {
        validateStep("Validate: every " + jsonPath + "=" + expectedValue, () -> {
            List<?> values = resp.jsonPath().getList(jsonPath);
            Assert.assertNotNull(values, "Expected values for path: " + jsonPath);
            Assert.assertFalse(values.isEmpty(), "Expected at least one value for path: " + jsonPath);

            for (Object actual : values) {
                StepLogger.logDetail("actual=" + actual);
                Assert.assertTrue(
                        Objects.equals(actual, expectedValue),
                        "Expected every " + jsonPath + " to equal " + expectedValue + " but found " + actual
                );
            }
        });
    }

    public static void errorFieldContains(Response resp, String fieldName, String expectedMessageFragment) {
        validateStep("Validate: error " + fieldName + "~=" + expectedMessageFragment, () -> {
            List<ApiValidationError> errors = validationErrors(resp);
            Assert.assertNotNull(errors, "Expected validation errors array");
            Assert.assertFalse(errors.isEmpty(), "Expected at least one validation error");

            for (ApiValidationError error : errors) {
                String actualField = error.field;
                String actualMessage = error.message;
                StepLogger.logDetail("field=" + actualField + ", message=" + actualMessage);

                if (Objects.equals(actualField, fieldName)
                        && actualMessage != null
                        && actualMessage.toString().contains(expectedMessageFragment)) {
                    return;
                }
            }

            Assert.fail("Expected validation error for field " + fieldName
                    + " containing message fragment: " + expectedMessageFragment);
        });
    }

    public static ApiErrorMessage errorMessage(Response resp) {
        return resp.as(ApiErrorMessage.class);
    }

    public static List<ApiValidationError> validationErrors(Response resp) {
        ApiValidationError[] errors = resp.as(ApiValidationError[].class);
        return Arrays.asList(errors);
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
