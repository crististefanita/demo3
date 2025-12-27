package com.endava.ai.api.tests;

import com.endava.ai.api.client.ApiActions;
import com.endava.ai.api.config.ConfigManager;
import com.endava.ai.api.core.BaseTestAPI;
import com.endava.ai.api.factory.UserFactory;
import com.endava.ai.api.model.UserRequest;
import com.endava.ai.api.service.UsersService;
import com.endava.ai.api.utils.JsonTestDataReader;
import com.endava.ai.api.utils.JsonUtils;
import com.endava.ai.api.validation.ResponseValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeUserTests extends BaseTestAPI {

    @Test(description = "Create user with invalid email should fail")
    public void invalid_email_is_rejected() {
        String invalidEmail = JsonTestDataReader.read("testdata/NegativeUserTests_invalid_email.json", InvalidEmailData.class).email;

        UsersService svc = new UsersService();
        UserRequest req = UserFactory.withEmail("invalid_email_is_rejected", invalidEmail);

        String fullUrl = ConfigManager.get("base.url") + svc.basePath();
        Response resp = ApiActions.execute(
                "POST",
                fullUrl,
                svc.basePath(),
                JsonUtils.toJson(req),
                () -> svc.createUser(req)
        );

        ResponseValidator.statusIs(resp, 422);
        Assert.assertTrue(resp.getBody().asString().contains("email"), "Expected email validation error");
    }

    private static class InvalidEmailData {
        String email;
    }
}
