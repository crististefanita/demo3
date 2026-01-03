package com.endava.ai.agentic;

import com.endava.ai.api.service.UsersService;
import org.testng.annotations.Test;
import com.endava.ai.api.core.BaseTestAPI;
import com.endava.ai.agentic.Models.*;

@SuppressWarnings("unused")
public final class AgenticE2ETest extends BaseTestAPI {
    private final UsersService usersService = new UsersService();

    @Test
    public void verifyAgenticResourcesOnClasspath() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        if (cl.getResource("agentic/Agentic_Bridge_Mapping_v1.yaml") == null) {
            throw new RuntimeException("NOT FOUND: agentic/Agentic_Bridge_Mapping_v1.yaml");
        }

        if (cl.getResource("agentic/ScenarioDefinition_v1.yaml") == null) {
            throw new RuntimeException("NOT FOUND: agentic/ScenarioDefinition_v1.yaml");
        }
    }

    @Test
    public void runResolvedExecutableScenario() {
        // Inputs
        ScenarioDefinition intent = YamlIO.readResource("agentic/ScenarioDefinition_v1.yaml", ScenarioDefinitionWrapper.class).example;
        BridgeMapping mapping = YamlIO.readResource("agentic/Agentic_Bridge_Mapping_v1.yaml", BridgeMapping.class);
        CapabilityRules rules = YamlIO.readResource("agentic/Agentic_Capability_Resolution_Rules_v1.yaml", CapabilityRules.class);

        // Resolve
        AgenticScenarioResolver resolver = new AgenticScenarioResolver();
        ExecutableScenario exec = resolver.resolve(intent, mapping, rules);

        // Execute using services available in BaseTestAPI-derived instance
        ScenarioRunner runner =
                new ScenarioRunner(
                        mapping,
                        new ReflectionServiceRegistry(this),
                        new DefaultPayloadFactoryRegistry(getClass().getSimpleName())
                );
        runner.run(exec);
    }

    // Wrapper to load the YAML that contains schema + example
    public static final class ScenarioDefinitionWrapper {
        public String scenarioDefinitionVersion;
        public Object schema;
        public ScenarioDefinition example;
    }
}