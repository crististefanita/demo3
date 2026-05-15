# Agentic API-Only Process — Reference Implementation (Minimal)

## What this ZIP contains
- YAML artifacts (intent, mapping, rules, resolved plan)
- Java reference implementation:
  - Resolver: ScenarioDefinition (intent) → ExecutableScenario (plan)
  - Runner: ExecutableScenario → Service.method calls via Bridge Mapping
  - TestNG test that wires everything together

## Inputs (classpath resources)
- `agentic/ScenarioDefinition_v1.yaml`
- `agentic/Agentic_Bridge_Mapping_v1.yaml`
- `agentic/Agentic_Capability_Resolution_Rules_v1.yaml`

## Output
- Resolver produces an `ExecutableScenario` in-memory.
- The resolved example scenario is also included as:
  - `agentic/ExecutableScenario_resolved_v1.yaml`

## Dependency note
This implementation uses SnakeYAML for YAML parsing:
- Maven: `org.yaml:snakeyaml`
- Gradle: `org.yaml:snakeyaml`

## Integration expectation
- Tests extend `BaseTestAPI` from `com.endava.ai.api.core`.
- Service instances (e.g., UsersService) are expected to be available as fields
  on the test instance or inherited from `BaseTestAPI`.