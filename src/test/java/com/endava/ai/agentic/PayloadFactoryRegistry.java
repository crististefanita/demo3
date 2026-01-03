package com.endava.ai.agentic;

public interface PayloadFactoryRegistry {
    Object create(Class<?> type);
}