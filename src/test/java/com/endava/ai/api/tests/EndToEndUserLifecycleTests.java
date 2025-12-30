package com.endava.ai.api.tests;

import com.endava.ai.api.core.BaseTestAPI;
import com.endava.ai.api.model.UserRequest;
import com.endava.ai.api.model.UserResponse;
import com.endava.ai.api.steps.UsersSteps;
import com.endava.ai.api.validation.ResponseValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EndToEndUserLifecycleTests extends BaseTestAPI {

    private UsersSteps users;
    private Integer createdUserId;

    @BeforeMethod
    public void setUp() {
        users = new UsersSteps();
        createdUserId = null;
    }

    @AfterMethod
    public void cleanUp() {
        if (createdUserId != null) {
            users.deleteUserSilently(createdUserId);
        }
    }

    @Test(description = "E2E: Create -> Get -> Update -> Delete -> Verify deletion")
    public void user_lifecycle_e2e() {
        UserResponse created = users.createValidUser();
        Assert.assertTrue(created.id > 0);
        createdUserId = created.id;

        Response getResp = users.getUser(createdUserId);
        ResponseValidator.statusIs(getResp, 200);

        UserRequest patch = new UserRequest();
        patch.status = "inactive";

        Response patchResp = users.patchUser(createdUserId, patch);
        ResponseValidator.statusIs(patchResp, 200);

        Response delResp = users.deleteUser(createdUserId);
        ResponseValidator.statusIs(delResp, 204);
        createdUserId = null;

        Response getAfterDel = users.getUser(created.id);
        // fail intentionally to check also the reporting of failed steps
        ResponseValidator.statusIs(getAfterDel, 404);
    }

}
