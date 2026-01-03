package com.endava.ai.agentic;

import java.util.*;
import com.endava.ai.agentic.Models.*;

public final class AgenticScenarioResolver {

    public ExecutableScenario resolve(ScenarioDefinition intent, BridgeMapping mapping, CapabilityRules rules) {
        ExecutableScenario exec = new ExecutableScenario();
        exec.sourceScenarioId = intent.scenarioId;
        exec.executableScenarioId = intent.scenarioId + "-exec";

        Map<String, Resource> resources = mapping.resources == null ? Map.of() : mapping.resources;

        Map<String, String> sourceToExecId = new HashMap<>();
        Set<String> keptSourceIds = new HashSet<>();

        for (ScenarioStep step : intent.steps) {
            ResolutionResult rr = resolveStep(step, resources, rules);

            if (!rr.isExecutable) {
                continue;
            }

            // Ensure dependencies are satisfied (source ids)
            if (step.dependsOn != null) {
                boolean depsOk = true;
                for (String dep : step.dependsOn) {
                    if (!keptSourceIds.contains(dep)) {
                        depsOk = false;
                        break;
                    }
                }
                if (!depsOk) {
                    continue;
                }
            }

            String execId = "exec-" + step.stepId;
            sourceToExecId.put(step.stepId, execId);
            keptSourceIds.add(step.stepId);

            ExecutableStep es = new ExecutableStep();
            es.stepId = execId;
            es.action = rr.action;
            es.resource = rr.resource;
            es.dependsOn = new ArrayList<>();

            if (step.dependsOn != null) {
                for (String dep : step.dependsOn) {
                    String depExec = sourceToExecId.get(dep);
                    if (depExec != null) es.dependsOn.add(depExec);
                }
            }

            Origin o = new Origin();
            o.sourceStepId = step.stepId;
            o.resolutionRuleId = rr.ruleId;
            es.origin = o;

            exec.steps.add(es);
        }

        if (exec.steps.size() < (intent.steps == null ? 0 : intent.steps.size())) {
            exec.executionNotes.add("Some intent steps were removed or degraded due to missing capabilities in Bridge Mapping.");
        }

        return exec;
    }

    private static final class ResolutionResult {
        final String action;
        final String resource;
        final String ruleId;
        final boolean isExecutable;
        ResolutionResult(String a, String r, String ruleId, boolean ok) {
            this.action = a; this.resource = r; this.ruleId = ruleId; this.isExecutable = ok;
        }
    }

    private ResolutionResult resolveStep(ScenarioStep step, Map<String, Resource> resources, CapabilityRules rules) {
        String action = step.action;
        String resource = step.resource;
        String ruleId = "direct-capability";

        for (int i = 0; i < 5; i++) {
            boolean exists = capabilityExists(resources, action, resource);
            if (exists) {
                return new ResolutionResult(action, resource, ruleId, true);
            }

            Rule matched = firstMatch(rules, action, resource, exists);
            if (matched == null) {
                return new ResolutionResult(action, resource, "unsupported-action", false);
            }

            ruleId = matched.id;

            if (matched.then != null && "remove".equalsIgnoreCase(matched.then.action)) {
                return new ResolutionResult(action, resource, ruleId, false);
            }

            if (matched.then != null && matched.then.transform != null && matched.then.transform.to != null) {
                if (matched.then.transform.to.action != null) action = matched.then.transform.to.action;
                if (matched.then.transform.to.resource != null) resource = matched.then.transform.to.resource;
                continue;
            }

            return new ResolutionResult(action, resource, ruleId, false);
        }

        return new ResolutionResult(action, resource, ruleId, false);
    }

    private boolean capabilityExists(Map<String, Resource> resources, String action, String resource) {
        Resource r = resources.get(resource);
        if (r == null || r.actions == null) return false;
        return r.actions.containsKey(action);
    }

    private Rule firstMatch(CapabilityRules rules, String action, String resource, boolean capabilityExists) {
        if (rules == null || rules.rules == null) return null;

        for (Rule r : rules.rules) {
            if ("direct-capability".equals(r.id)) continue;
            When w = r.when;
            if (w == null) continue;

            if (w.action != null && !w.action.equals(action)) continue;
            if (w.resource != null && !w.resource.equals(resource)) continue;
            if (w.capabilityExists != null && w.capabilityExists != capabilityExists) continue;

            return r;
        }
        return null;
    }
}