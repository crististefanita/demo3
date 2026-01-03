package com.endava.ai.agentic;

import com.endava.ai.api.service.UsersService;
import org.testng.annotations.Test;
import com.endava.ai.api.core.BaseTestAPI;
import com.endava.ai.agentic.Models.*;

@SuppressWarnings("unused")
public final class AgenticE2ETest extends BaseTestAPI {
    private final UsersService usersService = new UsersService();

    @Test
    public void runResolvedExecutableScenario() {
        BridgeMapping mapping =
                YamlIO.readResource(
                        "agentic/Agentic_Bridge_Mapping_v1.yaml",
                        BridgeMapping.class
                );

        CapabilityRules rules =
                YamlIO.readResource(
                        "agentic/Agentic_Capability_Resolution_Rules_v1.yaml",
                        CapabilityRules.class
                );

        AgenticScenarioResolver resolver = new AgenticScenarioResolver();
        ScenarioRunner runner =
                new ScenarioRunner(
                        mapping,
                        new ReflectionServiceRegistry(this),
                        new DefaultPayloadFactoryRegistry(getClass().getSimpleName())
                );

        ScenarioDefinition intent =
                YamlIO.readResource(
                        "agentic/ScenarioDefinition_create_delete_user.yaml",
                        ScenarioDefinition.class
                );

        ExecutableScenario exec = resolver.resolve(intent, mapping, rules);
        runner.run(exec);
    }

}