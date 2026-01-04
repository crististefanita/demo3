package com.endava.ai.api.tests.lifecycle;

import com.endava.ai.api.core.BaseTestAPI;
import com.endava.ai.api.model.UserResponse;
import com.endava.ai.api.steps.UsersSteps;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@SuppressWarnings("ALL")
public class BeforeTest_BeforeMethod_Test extends BaseTestAPI {

    private UsersSteps users;
    private UserResponse created;

    @BeforeTest
    public void beforeTestSetup() {
        users = new UsersSteps();
        created = users.createValidUser();
    }

    @BeforeMethod
    public void beforeMethodSetup() {
        Response getResp = users.waitUntilAvailable(created.id);
        Assert.assertEquals(getResp.getStatusCode(), 200);
    }

    @Test(description = "Delete previously created user")
    public void delete_user_2() {
        Response deleteResp = users.deleteUser(created.id);
        Assert.assertEquals(deleteResp.getStatusCode(), 204);
    }
}
