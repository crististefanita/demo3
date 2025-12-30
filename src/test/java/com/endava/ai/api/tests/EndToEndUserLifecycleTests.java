package com.endava.ai.api.tests;

import com.endava.ai.api.client.ApiActions;
import com.endava.ai.api.core.BaseTestAPI;
import com.endava.ai.api.factory.UserFactory;
import com.endava.ai.api.model.UserRequest;
import com.endava.ai.api.model.UserResponse;
import com.endava.ai.api.service.UsersService;
import com.endava.ai.api.utils.JsonUtils;
import com.endava.ai.api.validation.ResponseValidator;
import com.endava.ai.core.config.ConfigManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EndToEndUserLifecycleTests extends BaseTestAPI {

    @Test(description = "E2E: Create -> Get -> Update -> Delete -> Verify deletion")
    public void user_lifecycle_e2e() {
        UsersService svc = new UsersService();

        // Create
        UserRequest createReq = UserFactory.validUser("user_lifecycle_e2e_create");
        String fullUrl = ConfigManager.get("base.url") + svc.basePath();
        Response createResp = ApiActions.execute("POST", fullUrl, svc.basePath(), JsonUtils.toJson(createReq), () -> svc.createUser(createReq));
        ResponseValidator.statusIs(createResp, 201);
        int id = createResp.as(UserResponse.class).id;
        Assert.assertTrue(id > 0);

        try {
            // Get
            Response getResp = ApiActions.execute("GET", fullUrl + "/" + id, svc.basePath() + "/" + id, null, () -> svc.getUser(id));
            ResponseValidator.statusIs(getResp, 200);

            // Update (PATCH)
            UserRequest patch = new UserRequest();
            patch.status = "inactive";
            Response patchResp = ApiActions.execute("PATCH", fullUrl + "/" + id, svc.basePath() + "/" + id, JsonUtils.toJson(patch), () -> svc.patchUser(id, patch));
            ResponseValidator.statusIs(patchResp, 200);

            // Delete
            Response delResp = ApiActions.execute("DELETE", fullUrl + "/" + id, svc.basePath() + "/" + id, null, () -> svc.deleteUser(id));
            ResponseValidator.statusIs(delResp, 204);

            // Verify deletion
            Response getAfterDel = ApiActions.execute("GET", fullUrl + "/" + id, svc.basePath() + "/" + id, null, () -> svc.getUser(id));
            ResponseValidator.statusIs(getAfterDel, 404);
        } finally {
            // Best-effort cleanup: delete if still exists
            try { svc.deleteUser(id); } catch (Exception ignored) {}
        }
    }
}
