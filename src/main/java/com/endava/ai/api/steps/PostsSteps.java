package com.endava.ai.api.steps;

import com.endava.ai.api.client.ApiActions;
import com.endava.ai.api.model.PostRequest;
import com.endava.ai.api.service.PostsService;
import com.endava.ai.api.utils.JsonUtils;
import io.restassured.response.Response;

import java.util.Map;

import static com.endava.ai.api.client.HttpMethod.GET;
import static com.endava.ai.api.client.HttpMethod.POST;

public class PostsSteps {

    private final PostsService service = new PostsService();

    public Response listPosts(Map<String, ?> queryParams) {
        return ApiActions.execute(
                GET,
                service.basePath(),
                service.basePath(),
                null,
                () -> service.listPosts(queryParams)
        );
    }

    public Response getPost(Integer postId) {
        String path = service.basePath() + "/" + postId;

        return ApiActions.execute(
                GET,
                path,
                path,
                null,
                () -> service.getPost(postId)
        );
    }

    public Response listUserPosts(Integer userId, Map<String, ?> queryParams) {
        String path = "/public/v2/users/" + userId + "/posts";

        return ApiActions.execute(
                GET,
                path,
                path,
                null,
                () -> service.listUserPosts(userId, queryParams)
        );
    }

    public Response createPostForUser(Integer userId, PostRequest request) {
        String path = "/public/v2/users/" + userId + "/posts";

        return ApiActions.execute(
                POST,
                path,
                path,
                JsonUtils.toJson(request),
                () -> service.createUserPost(userId, request)
        );
    }
}
