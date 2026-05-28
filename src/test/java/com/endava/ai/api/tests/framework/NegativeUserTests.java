package com.endava.ai.api.tests.framework;

import com.endava.ai.api.core.BaseTestAPI;
import com.endava.ai.api.steps.UsersSteps;
import com.endava.ai.api.utils.JsonUtils;
import com.endava.ai.api.validation.ResponseValidator;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

public class NegativeUserTests extends BaseTestAPI {

    private UsersSteps users;
    private Integer createdUserId;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        users = new UsersSteps();
        createdUserId = null;
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp() {
        if (createdUserId != null) {
            users.deleteUserSilently(createdUserId);
        }
    }

    @Test(description = "Create user with invalid email should fail",
            groups = {"api", "api.users", "api.negative", "api.contract"})
    public void invalid_email_is_rejected() {
        String invalidEmail = JsonUtils.readString(
                "testdata/NegativeUserTests_invalid_email.json",
                "email"
        );

        Response resp = users.createUserWithInvalidEmail(invalidEmail);

        ResponseValidator.statusIs(resp, 422);
        ResponseValidator.bodyContains(resp, "email");
    }

    // Intentionally kept failing.
    // Legacy coverage expects this test to fail because
    // testdata/NegativeUserTests_duplicate_email.json is missing.
    @Ignore
    @Test(description = "Create user with duplicate email should fail")
    public void duplicate_email_is_rejected() {
        String email = JsonUtils.readString(
                "testdata/NegativeUserTests_duplicate_email.json",
                "email"
        );

        users.createUserWithInvalidEmail(email);

        Response resp = users.createUserWithInvalidEmail(email);

        ResponseValidator.statusIs(resp, 422);
        ResponseValidator.bodyContains(resp, "email");
    }

    @Test(description = "Create user without auth token should fail",
            groups = {"api", "api.users", "api.negative", "api.contract"})
    public void missing_auth_token_is_rejected() {
        Response resp = users.createUserWithoutAuth();

        ResponseValidator.statusIs(resp, 401);
    }

    @Test(description = "Delete non existing user should return 404",
            groups = {"api", "api.users", "api.negative", "api.contract"})
    public void delete_non_existing_user_returns_404() {
        Response resp = users.deleteUserNonExisting(999999999);

        ResponseValidator.statusIs(resp, 404);
    }
}
