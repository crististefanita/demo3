package com.endava.ai.agentic;

import java.lang.reflect.Method;
import java.util.*;
import com.endava.ai.agentic.Models.*;

public final class ScenarioRunner {
    private final BridgeMapping mapping;
    private final ServiceRegistry registry;
    private final PayloadFactoryRegistry payloadFactory;

    public ScenarioRunner(
            BridgeMapping mapping,
            ServiceRegistry registry,
            PayloadFactoryRegistry payloadFactory
    ) {
        this.mapping = mapping;
        this.registry = registry;
        this.payloadFactory = payloadFactory;
    }

    public void run(ExecutableScenario scenario) {
        if (scenario == null || scenario.steps == null) return;

        // naive topological execution: loop until all executed
        Set<String> executed = new HashSet<>();
        int safety = scenario.steps.size() * 5 + 10;

        while (executed.size() < scenario.steps.size() && safety-- > 0) {
            boolean progressed = false;

            for (ExecutableStep step : scenario.steps) {
                if (executed.contains(step.stepId)) continue;
                if (!depsSatisfied(step, executed)) continue;

                executeStep(step);
                executed.add(step.stepId);
                progressed = true;
            }

            if (!progressed) {
                throw new IllegalStateException("Cannot progress execution. Missing dependencies?");
            }
        }
    }

    private boolean depsSatisfied(ExecutableStep step, Set<String> executed) {
        if (step.dependsOn == null) return true;
        for (String dep : step.dependsOn) {
            if (!executed.contains(dep)) return false;
        }
        return true;
    }

    private void executeStep(ExecutableStep step) {
        Resource res = mapping.resources.get(step.resource);
        if (res == null || res.actions == null) throw new IllegalArgumentException("Unknown resource: " + step.resource);

        Action act = res.actions.get(step.action);
        if (act == null) throw new IllegalArgumentException("Unknown action: " + step.action + " for resource: " + step.resource);

        String serviceFqcn = mapping.base.apiServicePackage + "." + res.service;
        Object service = registry.getService(serviceFqcn);
        if (service == null) {
            throw new IllegalStateException("Service not found on test instance: " + serviceFqcn);
        }

        try {
            Method m = findMethod(service.getClass(), act.method);
            Object[] args = buildArgs(act);
            m.invoke(service, args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute step: " + step.stepId, e);
        }
    }

    private Method findMethod(Class<?> serviceClass, String methodName) {
        for (Method m : serviceClass.getMethods()) {
            if (m.getName().equals(methodName)) return m;
        }
        throw new IllegalArgumentException("Method not found: " + serviceClass.getName() + "." + methodName);
    }

    private Object[] buildArgs(Action act) {
        if (act.params == null || act.params.isEmpty()) {
            return new Object[0];
        }

        Object[] args = new Object[act.params.size()];
        for (int i = 0; i < act.params.size(); i++) {
            Param p = act.params.get(i);

            if (p.type == null) {
                args[i] = null;
                continue;
            }

            Class<?> clazz = resolveClass(p.type);
            args[i] = payloadFactory.create(clazz);
        }
        return args;
    }

    private Class<?> resolveClass(String type) {
        try {
            // tip complet
            if (type.contains(".")) {
                return Class.forName(type);
            }

            // tip relativ la apiModelPackage
            String fqcn = mapping.base.apiModelPackage + "." + type;
            return Class.forName(fqcn);

        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Cannot resolve parameter type: " + type, e);
        }
    }

}