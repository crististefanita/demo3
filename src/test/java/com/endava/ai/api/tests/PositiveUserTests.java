package com.endava.ai.api.tests;

import com.endava.ai.api.client.ApiActions;
import com.endava.ai.api.config.ConfigManager;
import com.endava.ai.api.core.BaseTestAPI;
import com.endava.ai.api.factory.UserFactory;
import com.endava.ai.api.model.UserRequest;
import com.endava.ai.api.model.UserResponse;
import com.endava.ai.api.service.UsersService;
import com.endava.ai.api.utils.JsonUtils;
import com.endava.ai.api.validation.ResponseValidator;
import com.endava.ai.api.validation.SchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveUserTests extends BaseTestAPI {

    @Test(description = "Create a user and validate response schema")
    public void create_user_success() {
        UsersService svc = new UsersService();
        UserRequest req = UserFactory.validUser("create_user_success");

        String fullUrl = ConfigManager.get("base.url") + svc.basePath();
        Response resp = ApiActions.execute(
                "POST",
                fullUrl,
                svc.basePath(),
                JsonUtils.toJson(req),
                () -> svc.createUser(req)
        );

        ResponseValidator.statusIs(resp, 201);
        SchemaValidator.validate(resp, "schemas/user.json");

        UserResponse created = resp.as(UserResponse.class);
        Assert.assertNotNull(created.id);
    }
}
