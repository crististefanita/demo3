package com.endava.ai.api.client;

import com.endava.ai.api.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public final class ApiClient {
    private static volatile boolean initialized = false;

    private ApiClient() {}

    public static synchronized void init() {
        if (initialized) return;
        String baseUrl = ConfigManager.get("base.url");
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("base.url is required in framework.properties");
        }
        RestAssured.baseURI = ConfigManager.getApiBaseUrl();
        initialized = true;
    }

    public static RequestSpecification request() {
        init();
        RequestSpecification req = RestAssured.given()
                .contentType(JSON)
                .accept(JSON);

        String token = ConfigManager.get("auth.token");
        if (token != null && !token.isBlank()) {
            req.header("Authorization", "Bearer " + token.trim());
        }
        return req;
    }
}
