package com.endava.ai.api.tests;

import com.endava.ai.api.core.BaseTestAPI;
import com.endava.ai.api.model.UserResponse;
import com.endava.ai.api.steps.UsersSteps;
import com.endava.ai.api.validation.SchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PositiveUserTests extends BaseTestAPI {
    private UsersSteps users;

    @BeforeMethod
    public void setUp() {
        users = new UsersSteps();
    }

    @Test(description = "Create a user and validate response schema")
    public void create_user_success() {
        Response resp = users.createValidUserResponse();
        SchemaValidator.validate(resp, "schemas/user.json");

        UserResponse created = resp.as(UserResponse.class);
        Assert.assertNotNull(created.id);
    }

    @Test(description = "Create user, wait until available, then delete")
    public void create_wait_and_delete_user() {
        UserResponse created = users.createValidUser();
        Assert.assertNotNull(created.id);

        Response getResp = users.waitUntilAvailable(created.id);
        Assert.assertEquals(getResp.getStatusCode(), 200);

        Response deleteResp = users.deleteUser(created.id);
        Assert.assertEquals(deleteResp.getStatusCode(), 204);
    }
}
