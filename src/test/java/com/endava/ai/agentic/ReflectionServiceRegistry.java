package com.endava.ai.agentic;

import java.lang.reflect.Field;

public final class ReflectionServiceRegistry implements ServiceRegistry {
    private final Object testInstance;

    public ReflectionServiceRegistry(Object testInstance) {
        this.testInstance = testInstance;
    }

    @Override
    public Object getService(String fqcn) {
        if (testInstance == null) return null;
        Class<?> cls = testInstance.getClass();

        // Walk class hierarchy to find a field assignable to fqcn
        while (cls != null) {
            for (Field f : cls.getDeclaredFields()) {
                try {
                    f.setAccessible(true);
                    Object v = f.get(testInstance);
                    if (v == null) continue;
                    if (v.getClass().getName().equals(fqcn)) return v;
                    // also allow assignable (proxy/subclass)
                    Class<?> target = Class.forName(fqcn);
                    if (target.isAssignableFrom(v.getClass())) return v;
                } catch (Exception ignored) {
                }
            }
            cls = cls.getSuperclass();
        }

        return null;
    }
}