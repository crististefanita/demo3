package com.endava.ai.core.listener.internal;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public final class FlowPolicy {

    private final Map<Class<?>, Set<String>> dependentsPerClass = new IdentityHashMap<>();

    public boolean isFlowClass(ITestContext ctx, Class<?> cls) {
        if (ctx == null) return false;
        return !getDependents(ctx, cls).isEmpty();
    }

    public boolean isLastInFlow(ITestResult result) {
        Class<?> cls = result.getMethod().getRealClass();
        Set<String> deps = dependentsPerClass.get(cls);
        if (deps == null) return true;
        return !deps.contains(result.getMethod().getMethodName());
    }

    private Set<String> getDependents(ITestContext ctx, Class<?> cls) {
        return dependentsPerClass.computeIfAbsent(cls, c -> computeDependents(ctx, cls));
    }

    private static Set<String> computeDependents(ITestContext ctx, Class<?> cls) {
        Set<String> s = new HashSet<>();
        for (ITestNGMethod m : ctx.getAllTestMethods()) {
            if (m == null || m.getRealClass() == null || !cls.equals(m.getRealClass())) continue;
            String[] deps = m.getMethodsDependedUpon();
            if (deps == null) continue;
            for (String d : deps) {
                if (d != null && !d.isBlank()) s.add(d);
            }
        }
        return s;
    }

    public void clear() {
        dependentsPerClass.clear();
    }
}
