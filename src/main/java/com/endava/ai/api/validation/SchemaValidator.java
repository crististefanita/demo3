package com.endava.ai.api.validation;

import com.endava.ai.core.reporting.StepLogger;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

public final class SchemaValidator {
    private SchemaValidator() {}

    public static void validate(Response resp, String schemaClasspath) {
        StepLogger.startStep("Validate JSON schema");
        try {
            StepLogger.logDetail("Validate: schema=" + schemaClasspath);
            resp.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaClasspath));
            StepLogger.pass("Schema valid");
        } catch (AssertionError e) {
            StepLogger.fail(e.getMessage(), e);
            throw e;
        }
    }
}
