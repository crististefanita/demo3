package com.endava.ai.api.client;

import com.endava.ai.core.reporting.StepLogger;
import io.restassured.response.Response;

import java.time.Duration;

import static com.endava.ai.core.config.ConfigManager.require;

public final class ApiActions {

    private static final String MAX_RATE_LIMIT_RETRIES_KEY = "api.rate.limit.max.retries";
    private static final String DEFAULT_RATE_LIMIT_DELAY_SECONDS_KEY = "api.rate.limit.retry.default.delay.seconds";
    private static final String MAX_RATE_LIMIT_DELAY_SECONDS_KEY = "api.rate.limit.retry.max.delay.seconds";
    private static final int DEFAULT_MAX_RATE_LIMIT_RETRIES = 2;
    private static final long DEFAULT_RATE_LIMIT_DELAY_SECONDS = 5L;
    private static final long DEFAULT_MAX_RATE_LIMIT_DELAY_SECONDS = 65L;

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
            Response response = null;
            int maxRetries = getConfiguredInt(MAX_RATE_LIMIT_RETRIES_KEY, DEFAULT_MAX_RATE_LIMIT_RETRIES);

            for (int attempt = 0; attempt <= maxRetries; attempt++) {
                if (attempt > 0) {
                    StepLogger.logDetail("rate-limit.retry.attempt=" + attempt);
                }

                logRequest(method, fullUrl, requestBodyJson);
                response = call.run();
                logResponse(response);

                if (response.getStatusCode() != 429 || attempt == maxRetries) {
                    StepLogger.pass("Call completed");
                    return response;
                }

                Duration retryDelay = resolveRateLimitDelay(response);
                StepLogger.logDetail("rate-limit.retry.delay.ms=" + retryDelay.toMillis());
                sleep(retryDelay);
            }

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

            if (getBoolean("console.details.enabled")) {
                System.out.println("  • " + bodyJson);
            }

            StepLogger.logCodeBlock(bodyJson);
        }
    }

    private static void logResponse(Response response) {
        StepLogger.logDetail("response.status=" + response.getStatusCode());
        StepLogger.logDetail("response.body");

        String bodyJson = response.getBody().asPrettyString();

        if (getBoolean("console.details.enabled")) {
            System.out.println("  • " + bodyJson);
        }

        StepLogger.logCodeBlock(bodyJson);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(require(key));
    }

    private static Duration resolveRateLimitDelay(Response response) {
        Duration defaultDelay = configuredDuration(
                DEFAULT_RATE_LIMIT_DELAY_SECONDS_KEY,
                DEFAULT_RATE_LIMIT_DELAY_SECONDS
        );
        Duration maxDelay = configuredDuration(
                MAX_RATE_LIMIT_DELAY_SECONDS_KEY,
                DEFAULT_MAX_RATE_LIMIT_DELAY_SECONDS
        );
        String retryAfterHeader = firstNonBlank(
                response.getHeader("Retry-After"),
                response.getHeader("retry-after"),
                response.getHeader("x-ratelimit-reset"),
                response.getHeader("X-RateLimit-Reset")
        );

        if (retryAfterHeader == null) {
            return defaultDelay;
        }

        try {
            long seconds = Long.parseLong(retryAfterHeader.trim());
            if (seconds <= 0) {
                return defaultDelay;
            }

            Duration parsed = Duration.ofSeconds(seconds);
            return parsed.compareTo(maxDelay) > 0
                    ? maxDelay
                    : parsed;
        } catch (NumberFormatException ignored) {
            return defaultDelay;
        }
    }

    private static int getConfiguredInt(String key, int defaultValue) {
        String value = com.endava.ai.core.config.ConfigManager.get(key, Integer.toString(defaultValue));
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Invalid integer config key: " + key + "=" + value, e);
        }
    }

    private static Duration configuredDuration(String key, long defaultSeconds) {
        String value = com.endava.ai.core.config.ConfigManager.get(key, Long.toString(defaultSeconds));
        try {
            long parsed = Long.parseLong(value);
            if (parsed <= 0) {
                throw new IllegalStateException("Config key must be > 0: " + key + "=" + value);
            }
            return Duration.ofSeconds(parsed);
        } catch (NumberFormatException e) {
            throw new IllegalStateException("Invalid duration config key: " + key + "=" + value, e);
        }
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    private static void sleep(Duration delay) {
        try {
            Thread.sleep(delay.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for API rate limit reset", e);
        }
    }

    @FunctionalInterface
    public interface ApiCall {
        Response run();
    }
}
