package com.endava.ai.api.tests.lifecycle;

import com.endava.ai.api.core.BaseTestAPI;
import com.endava.ai.api.model.UserResponse;
import com.endava.ai.api.steps.UsersSteps;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@SuppressWarnings("ALL")
public class BeforeTestOne_TestTwo extends BaseTestAPI {

    private UsersSteps users;
    private UserResponse created;

    @BeforeTest
    public void beforeTestSetup() {
        users = new UsersSteps();
        created = users.createValidUser();
    }

    @Test(description = "Wait until created user is available")
    public void wait_until_user_is_available() {
        Response getResp = users.waitUntilAvailable(created.id);
        Assert.assertEquals(getResp.getStatusCode(), 200);
    }

    @Test(
            description = "Delete previously created user",
            dependsOnMethods = "wait_until_user_is_available"
    )
    public void delete_user_5() {
        Response deleteResp = users.deleteUser(created.id);
        Assert.assertEquals(deleteResp.getStatusCode(), 204);
    }
}
