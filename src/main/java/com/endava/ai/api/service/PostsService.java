package com.endava.ai.api.service;

import com.endava.ai.api.client.ApiClient;
import com.endava.ai.api.model.PostRequest;
import io.restassured.response.Response;

import java.util.Map;

public class PostsService {
    private static final String BASE_PATH = "/public/v2/posts";

    public Response listPosts(Map<String, ?> queryParams) {
        return ApiClient.request()
                .queryParams(queryParams)
                .get(BASE_PATH)
                .andReturn();
    }

    public Response getPost(int postId) {
        return ApiClient.request()
                .get(BASE_PATH + "/" + postId)
                .andReturn();
    }

    public Response listUserPosts(int userId, Map<String, ?> queryParams) {
        return ApiClient.request()
                .queryParams(queryParams)
                .get("/public/v2/users/" + userId + "/posts")
                .andReturn();
    }

    public Response createUserPost(int userId, PostRequest request) {
        return ApiClient.request()
                .body(request)
                .post("/public/v2/users/" + userId + "/posts")
                .andReturn();
    }

    public String basePath() {
        return BASE_PATH;
    }
}
