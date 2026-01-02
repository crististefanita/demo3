package com.endava.ai.api.service;

import com.endava.ai.api.client.ApiClient;
import com.endava.ai.api.model.UserRequest;
import io.restassured.response.Response;

public class UsersService {
    private static final String BASE_PATH = "/public/v2/users";

    public Response createUser(UserRequest req) {
        return ApiClient.request()
                .body(req)
                .post(BASE_PATH)
                .andReturn();
    }

    public Response getUser(int id) {
        return ApiClient.request()
                .get(BASE_PATH + "/" + id)
                .andReturn();
    }

    @SuppressWarnings("unused")
    public Response putUser(int id, UserRequest req) {
        return ApiClient.request()
                .body(req)
                .put(BASE_PATH + "/" + id)
                .andReturn();
    }

    public Response patchUser(int id, UserRequest req) {
        return ApiClient.request()
                .body(req)
                .patch(BASE_PATH + "/" + id)
                .andReturn();
    }

    public Response deleteUser(int id) {
        return ApiClient.request()
                .delete(BASE_PATH + "/" + id)
                .andReturn();
    }

    public String basePath() {
        return BASE_PATH;
    }
}
