package com.endava.ai.api.factory;

import com.endava.ai.api.model.UserRequest;
import com.endava.ai.api.utils.DataGenerator;

import java.util.Random;

public final class UserFactory {
    private UserFactory() {}

    public static UserRequest validUser(String testName) {
        Random r = DataGenerator.forTest(testName);
        UserRequest u = new UserRequest();
        u.name = DataGenerator.randomName(r);
        u.email = DataGenerator.randomEmail(r);
        u.gender = "male";
        u.status = "active";
        return u;
    }

    public static UserRequest withEmail(String testName, String email) {
        UserRequest u = validUser(testName);
        u.email = email;
        return u;
    }
}
