package com.endava.ai.api.client;

import com.endava.ai.core.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public final class ApiClient {
    private static volatile boolean initialized = false;

    private ApiClient() {
    }

    public static synchronized void init() {
        if (initialized) return;
        String baseUrl = ConfigManager.require("base.url.api");
        if (baseUrl.isBlank()) {
            throw new IllegalStateException("base.url.api is required in framework.properties");
        }
        RestAssured.baseURI = baseUrl;
        initialized = true;
    }

    public static RequestSpecification request() {
        init();
        RequestSpecification req = RestAssured.given()
                .contentType(JSON)
                .accept(JSON);

        String token = ConfigManager.require("auth.token");
        if (!token.isBlank()) {
            req.header("Authorization", "Bearer " + token);
        }
        return req;
    }

    public static RequestSpecification requestWithoutAuth() {
        init();
        return RestAssured.given()
                .contentType(JSON)
                .accept(JSON);
    }
}
