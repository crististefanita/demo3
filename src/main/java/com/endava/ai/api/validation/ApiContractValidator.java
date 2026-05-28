package com.endava.ai.api.validation;

import com.endava.ai.core.reporting.StepLogger;
import io.restassured.response.Response;

import java.util.Map;

public final class ApiContractValidator {

    private ApiContractValidator() {
    }

    public static void paginatedJsonCollection(Response resp, int expectedPage, int expectedLimit, String schemaClasspath) {
        contractStep("Contract: PAGINATION_VALIDATION", () -> {
            ResponseValidator.contentTypeContains(resp, "application/json");
            ResponseValidator.headerEquals(resp, "X-Pagination-Page", String.valueOf(expectedPage));
            ResponseValidator.headerEquals(resp, "X-Pagination-Limit", String.valueOf(expectedLimit));
            ResponseValidator.arrayNotEmpty(resp);
            ResponseValidator.arraySizeAtMost(resp, expectedLimit);
            SchemaValidator.validate(resp, schemaClasspath);
        });
    }

    public static void filteredJsonCollection(Response resp,
                                              int expectedMaxSize,
                                              String schemaClasspath,
                                              Map<String, Object> expectedFieldValues) {
        contractStep("Contract: FILTER_VALIDATION", () -> {
            ResponseValidator.contentTypeContains(resp, "application/json");
            ResponseValidator.arrayNotEmpty(resp);
            ResponseValidator.arraySizeAtMost(resp, expectedMaxSize);

            for (Map.Entry<String, Object> entry : expectedFieldValues.entrySet()) {
                ResponseValidator.everyFieldEquals(resp, entry.getKey(), entry.getValue());
            }

            SchemaValidator.validate(resp, schemaClasspath);
        });
    }

    public static void paginatedFilteredJsonCollection(Response resp,
                                                       int expectedPage,
                                                       int expectedLimit,
                                                       String schemaClasspath,
                                                       Map<String, Object> expectedFieldValues) {
        contractStep("Contract: PAGINATION_FILTER_VALIDATION", () -> {
            paginatedJsonCollection(resp, expectedPage, expectedLimit, schemaClasspath);

            for (Map.Entry<String, Object> entry : expectedFieldValues.entrySet()) {
                ResponseValidator.everyFieldEquals(resp, entry.getKey(), entry.getValue());
            }
        });
    }

    public static void jsonMessageContract(Response resp, int expectedStatus, String expectedMessage, String schemaClasspath) {
        contractStep("Contract: ERROR_MESSAGE_CONTRACT", () -> {
            ResponseValidator.statusIs(resp, expectedStatus);
            ResponseValidator.contentTypeContains(resp, "application/json");
            ResponseValidator.messageEquals(resp, expectedMessage);
            SchemaValidator.validate(resp, schemaClasspath);
        });
    }

    public static void jsonValidationErrorContract(Response resp,
                                                   int expectedStatus,
                                                   String schemaClasspath,
                                                   Map<String, String> expectedErrors) {
        contractStep("Contract: VALIDATION_ERROR_CONTRACT", () -> {
            ResponseValidator.statusIs(resp, expectedStatus);
            ResponseValidator.contentTypeContains(resp, "application/json");

            for (Map.Entry<String, String> entry : expectedErrors.entrySet()) {
                ResponseValidator.errorFieldContains(resp, entry.getKey(), entry.getValue());
            }

            SchemaValidator.validate(resp, schemaClasspath);
        });
    }

    public static void rateLimitHeadersPresent(Response resp) {
        contractStep("Contract: RATE_LIMIT_DIAGNOSTICS", () -> {
            ResponseValidator.headerPresent(resp, "x-ratelimit-limit");
            ResponseValidator.headerPresent(resp, "x-ratelimit-remaining");
            ResponseValidator.headerPresent(resp, "x-ratelimit-reset");
        });
    }

    private static void contractStep(String title, Runnable assertion) {
        StepLogger.startStep(title);
        try {
            assertion.run();
            StepLogger.pass("Contract validated");
        } catch (AssertionError e) {
            StepLogger.fail(e.getMessage(), e);
            throw e;
        }
    }
}
