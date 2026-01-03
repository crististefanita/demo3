package com.endava.ai.agentic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public final class Models {
    private Models() {
    }

    public static final class ScenarioDefinition {
        public String scenarioId;
        public String name;
        public String goal;
        public List<ScenarioStep> steps = new ArrayList<>();
        public Cleanup cleanup;
    }

    public static final class ScenarioStep {
        public String stepId;
        public String action;
        public String resource;
        public List<String> dependsOn = new ArrayList<>();
        public List<Capture> captures = new ArrayList<>();
    }

    public static final class Capture {
        public String name;
        public String from;
        public String type;
    }

    public static final class Cleanup {
        public String strategy;
    }

    public static final class ExecutableScenario {
        public String executableScenarioVersion = "v1";
        public String executableScenarioId;
        public String sourceScenarioId;
        public List<ExecutableStep> steps = new ArrayList<>();
        public List<String> executionNotes = new ArrayList<>();
    }

    public static final class ExecutableStep {
        public String stepId;
        public String action;
        public String resource;
        public List<String> dependsOn = new ArrayList<>();
        public Origin origin;
    }

    public static final class Origin {
        public String sourceStepId;
        public String resolutionRuleId;
    }

    // Bridge mapping (minimal fields needed for runtime lookup)
    public static final class BridgeMapping {
        public String bridgeMappingVersion;
        public Base base;
        public Map<String, Resource> resources;
        public List<String> conventions;
    }

    public static final class Base {
        public String apiServicePackage;
        public String apiModelPackage;
        public String responseType;
    }

    public static final class Resource {
        public String service;
        public String basePath;
        public Map<String, Action> actions;
    }

    public static final class Action {
        public String method;
        public List<Param> params;
        public List<Capture> captures;
    }

    public static final class Param {
        public String name;
        public String type;
    }

    // Capability rules (minimal fields needed)
    public static final class CapabilityRules {
        public String capabilityResolutionVersion;

        // Metadata
        public List<String> principles;
        public List<String> resolutionOrder;
        public Map<String, Object> outputs;
        public List<String> notes;

        // Rules
        public List<Rule> rules;
    }

    public static final class Rule {
        public String id;
        public When when;
        public Then then;

        // Metadata din YAML
        public String note;
        public String severity;
    }

    public static final class When {
        public String action;
        public String resource;
        public Boolean capabilityExists;
    }

    public static final class Then {
        public String action;        // execute / remove
        public Transform transform;  // optional
        public String rationale;     // <-- NECESAR (YAML direct sub then)
    }

    public static final class Transform {
        public To to;                // <-- OBLIGATORIU
        public String rationale;     // <-- NECESAR (YAML sub transform)
    }

    public static final class To {
        public String action;
        public String resource;
    }
}