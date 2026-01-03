package com.endava.ai.agentic;

import com.endava.ai.api.factory.UserFactory;
import com.endava.ai.api.model.UserRequest;

public final class DefaultPayloadFactoryRegistry
        implements PayloadFactoryRegistry {

    private final String testName;

    public DefaultPayloadFactoryRegistry(String testName) {
        this.testName = testName;
    }

    @Override
    public Object create(Class<?> type) {
        if (type.equals(UserRequest.class)) {
            return UserFactory.validUser(testName);
        }
        return null;
    }
}
