package com.endava.ai.api.client;

import com.endava.ai.core.config.ConfigManager;
import com.endava.ai.core.reporting.ReportingManager;
import com.endava.ai.core.reporting.StepLogger;
import io.restassured.response.Response;

public final class ApiActions {

    private ApiActions() {
    }

    public static Response execute(
            String method,
            String fullUrl,
            String relativePath,
            String requestBodyJson,
            ApiCall call) {

        StepLogger.startStep(method + " " + relativePath);

        try {
            logRequest(method, fullUrl, requestBodyJson);

            Response response = call.run();

            logResponse(response);

            StepLogger.pass("Call completed");
            return response;

        } catch (Exception e) {
            StepLogger.fail("API call failed", e);
            throw e;
        }
    }

    private static void logRequest(String method, String fullUrl, String bodyJson) {
        StepLogger.logDetail("method=" + method);
        StepLogger.logDetail("url=" + fullUrl);

        if (bodyJson != null && !bodyJson.isBlank()) {
            StepLogger.logDetail("request.body");

            if (ConfigManager.getBoolean("console.details.enabled")) {
                System.out.println("  • " + bodyJson);
            }

            ReportingManager.getLogger().logCodeBlock(bodyJson);
        }
    }

    private static void logResponse(Response response) {
        StepLogger.logDetail("response.status=" + response.getStatusCode());
        StepLogger.logDetail("response.body");

        String bodyJson = response.getBody().asPrettyString();

        if (ConfigManager.getBoolean("console.details.enabled")) {
            System.out.println("  • " + bodyJson);
        }

        ReportingManager.getLogger().logCodeBlock(bodyJson);
    }

    @FunctionalInterface
    public interface ApiCall {
        Response run();
    }
}
