package com.endava.ai.api.client;

import com.endava.ai.ui.config.ConfigManager;
import com.endava.ai.ui.reporting.ReportingManager;
import com.endava.ai.ui.reporting.StepLogger;
import io.restassured.response.Response;

public final class ApiActions {

    private ApiActions() {}

    public static Response execute(
            String method,
            String fullUrl,
            String relativePath,
            String requestBodyJson,
            ApiCall call) {

        StepLogger.startStep(method + " " + relativePath);

        try {
            StepLogger.logDetail("method=" + method);
            StepLogger.logDetail("url=" + fullUrl);

            if (requestBodyJson != null && !requestBodyJson.isBlank()) {
                StepLogger.logDetail("request.body");
                if (ConfigManager.getBoolean("console.details.enabled")) {
                    System.out.println("  • " + requestBodyJson);
                }
                ReportingManager.getLogger().logCodeBlock(requestBodyJson);
            }

            Response response = call.run();

            StepLogger.logDetail("response.status=" + response.getStatusCode());
            StepLogger.logDetail("response.body");
            String bodyJson = response.getBody().asPrettyString();
            if (ConfigManager.getBoolean("console.details.enabled")) {
                System.out.println("  • " + bodyJson);
            }
            ReportingManager.getLogger().logCodeBlock(bodyJson);

            StepLogger.pass("Call completed");
            return response;

        } catch (Exception e) {
            StepLogger.fail("API call failed", e);
            throw e;
        }
    }

    @FunctionalInterface
    public interface ApiCall {
        Response run();
    }
}
