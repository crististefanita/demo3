package com.endava.ai.api.steps;

import com.endava.ai.api.client.ApiActions;
import com.endava.ai.api.client.ApiClient;
import com.endava.ai.api.factory.UserFactory;
import com.endava.ai.api.model.UserRequest;
import com.endava.ai.api.model.UserResponse;
import com.endava.ai.api.service.UsersService;
import com.endava.ai.api.utils.JsonUtils;
import com.endava.ai.api.validation.ResponseValidator;
import io.restassured.response.Response;

import static com.endava.ai.api.client.HttpMethod.*;

public class UsersSteps {

    private static final String CREATE_VALID_USER = "create_user";
    private static final String CREATE_INVALID_EMAIL = "invalid_email_is_rejected";

    private final UsersService svc = new UsersService();

    public UserResponse createValidUser() {
        return createAndValidateUser(
                UserFactory.validUser(CREATE_VALID_USER)
        ).as(UserResponse.class);
    }

    public Response createValidUserResponse() {
        return createAndValidateUser(
                UserFactory.validUser(CREATE_VALID_USER)
        );
    }

    public Response createUserWithInvalidEmail(String invalidEmail) {
        return createUser(
                UserFactory.withEmail(CREATE_INVALID_EMAIL, invalidEmail)
        );
    }

    public Response createUserWithoutAuth() {
        UserRequest req = UserFactory.validUser("missing_auth");

        return ApiActions.execute(
                POST,
                svc.basePath(),
                svc.basePath(),
                JsonUtils.toJson(req),
                () -> ApiClient.requestWithoutAuth()
                        .body(req)
                        .post(svc.basePath())
        );
    }

    public Response getUser(Integer id) {
        String path = svc.basePath() + "/" + id;

        return ApiActions.execute(
                GET,
                path,
                path,
                null,
                () -> svc.getUser(id)
        );
    }

    public Response waitUntilAvailable(Integer userId) {
        return ResponseValidator.waitForStatus(
                () -> svc.getUser(userId),
                200
        );
    }

     public Response patchUser(Integer id, UserRequest patch) {
        String path = svc.basePath() + "/" + id;

        return ApiActions.execute(
                PATCH,
                path,
                path,
                JsonUtils.toJson(patch),
                () -> svc.patchUser(id, patch)
        );
    }

    public Response deleteUser(Integer userId) {
        String path = svc.basePath() + "/" + userId;

        Response resp = ApiActions.execute(
                DELETE,
                path,
                path,
                null,
                () -> svc.deleteUser(userId)
        );

        ResponseValidator.statusIs(resp, 204);
        return resp;
    }

    public Response deleteUserNonExisting(Integer id) {
        String path = svc.basePath() + "/" + id;

        return ApiActions.execute(
                DELETE,
                path,
                path,
                null,
                () -> svc.deleteUser(id)
        );
    }

    public void deleteUserSilently(Integer userId) {
        try {
            svc.deleteUser(userId);
        } catch (Exception ignored) {
            // cleanup best-effort, fără reporting
        }
    }

    private Response createAndValidateUser(UserRequest req) {
        Response resp = createUser(req);
        ResponseValidator.statusIs(resp, 201);
        return resp;
    }

    private Response createUser(UserRequest req) {
        return ApiActions.execute(
                POST,
                svc.basePath(),
                svc.basePath(),
                JsonUtils.toJson(req),
                () -> svc.createUser(req)
        );
    }
}
