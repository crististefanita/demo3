package com.endava.ai.api.tests;

import com.endava.ai.api.core.BaseTestAPI;
import com.endava.ai.api.steps.UsersSteps;
import com.endava.ai.api.utils.JsonUtils;
import com.endava.ai.api.validation.ResponseValidator;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NegativeUserTests extends BaseTestAPI {

    private UsersSteps users;

    @BeforeMethod
    public void setUp() {
        users = new UsersSteps();
    }

    @Test(description = "Create user with invalid email should fail")
    public void invalid_email_is_rejected() {
        String invalidEmail = JsonUtils.readString(
                "testdata/NegativeUserTests_invalid_email.json",
                "email"
        );

        Response resp = users.createUserWithInvalidEmail(invalidEmail);

        ResponseValidator.statusIs(resp, 422);
        ResponseValidator.bodyContains(resp, "email");
    }

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

    @Test(description = "Create user without auth token should fail")
    public void missing_auth_token_is_rejected() {
        Response resp = users.createUserWithoutAuth();

        ResponseValidator.statusIs(resp, 401);
    }

    @Test(description = "Delete non existing user should return 404")
    public void delete_non_existing_user_returns_404() {
        Response resp = users.deleteUserNonExisting(999999999);

        ResponseValidator.statusIs(resp, 404);
    }
}
