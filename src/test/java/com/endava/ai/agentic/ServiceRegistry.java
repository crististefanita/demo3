package com.endava.ai.agentic;

public interface ServiceRegistry {
    Object getService(String fullyQualifiedClassName);
}