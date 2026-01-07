package com.endava.ai.api.tests.lifecycle;

import com.endava.ai.api.core.BaseTestAPI;
import com.endava.ai.api.model.UserRequest;
import com.endava.ai.api.model.UserResponse;
import com.endava.ai.api.steps.UsersSteps;
import com.endava.ai.api.validation.ResponseValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

@SuppressWarnings("ALL")
public class BeforeClassMethod_After extends BaseTestAPI {

    private UsersSteps users;
    private Integer createdUserId;
    private UserResponse created;

    @BeforeClass
    public void setUp() {
        users = new UsersSteps();
        createdUserId = null;
    }

    @BeforeTest
    public void beforeTestSetup() {
        users = new UsersSteps();
        created = users.createValidUser();
        Assert.assertTrue(created.id > 0);
        createdUserId = created.id;
    }

    @BeforeMethod
    public void beforeMethodSetup() {
        if (createdUserId == null) {
            created = users.createValidUser();
            Assert.assertTrue(created.id > 0);
            createdUserId = created.id;
        }

        Response getResp = users.waitUntilAvailable(createdUserId);
        Assert.assertEquals(getResp.getStatusCode(), 200);
    }

    @Test(description = "Update User")
    public void update_User() {
        UserRequest patch = new UserRequest();
        patch.status = "inactive";

        Response patchResp = users.patchUser(createdUserId, patch);
        ResponseValidator.statusIs(patchResp, 200);
    }

    @AfterMethod
    public void deleteUser_after() {
        Response delResp = users.deleteUser(createdUserId);
        ResponseValidator.statusIs(delResp, 204);
        createdUserId = null;

        Response getAfterDel = users.getUser(created.id);
        ResponseValidator.statusIs(getAfterDel, 404);
    }

    @AfterClass
    public void cleanUp() {
        if (createdUserId != null) {
            users.deleteUserSilently(createdUserId);
        }
    }
}
