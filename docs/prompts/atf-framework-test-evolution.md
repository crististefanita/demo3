# ATF Framework Test Evolution Prompt

Reusable Codex/MCP prompt template for incrementally strengthening framework-level and component-level test coverage around existing ATF behavior, modified classes, and reusable contracts.

You are continuing work inside an EXISTING enterprise-grade Java Automation Testing Framework (ATF).

The current repository structure, framework architecture, lifecycle orchestration, reporting system, execution model, and existing test strategy are the SOURCE OF TRUTH.

Do NOT invent a parallel framework.
Do NOT redesign the architecture.
Do NOT replace existing framework patterns.
Do NOT rewrite existing stable behavior unless a change is strictly required to cover a real gap.

GOAL
==================================================
Incrementally improve the existing ATF by adding or strengthening unit/component-level tests for newly identified uncovered cases, while preserving the current behavior and the existing modifications already made in framework classes.

This framework is expected to evolve continuously through safe iterations.
Treat this task as part of that ongoing ATF evolution process.

PRIMARY OBJECTIVE
==================================================
Focus strictly on strengthening coverage around EXISTING modified classes and EXISTING framework behavior.

Ignore adding new business/UI suites unless they are strictly necessary for validating a framework contract.

Prioritize test additions under:
- src/test/java/com/endava/ai/atf
- src/test/java/com/endava/ai/atf/ui

Preserve all existing modifications in current production classes unless a real defect is proven.

ANALYSIS FIRST
==================================================
Before making changes:

1. Analyze the current repository carefully.
2. Read the existing framework guidance and prompt documents if present, especially:
   - README.md
   - docs/prompts/ui-recording-to-atf-tests.md
   - docs/prompts/api-framework-evolution.md
3. Infer the intended framework evolution direction from those documents.
4. Inspect existing test patterns and reuse them.
5. Identify the smallest valuable uncovered cases around the already modified existing classes.

SCOPE RULES
==================================================
Only extend the current framework and test strategy.

Prefer covering existing modified classes such as:
- framework utilities
- validation helpers
- engine behavior
- lifecycle-safe UI helpers
- reporting-adjacent framework contracts

Do NOT create:
- framework-v2 abstractions
- duplicate test harnesses
- parallel driver/reporting/screenshot systems
- speculative helper layers that the framework does not already use

TESTING STYLE RULES
==================================================
Prefer the existing repository’s style for:
- contract tests
- component tests
- smoke contract tests
- fake/stub-based framework verification
- deterministic assertions

Prefer validating behavior through stable contracts rather than through implementation-heavy mocking.

If static-heavy code exists, use mocking carefully and only when it matches the existing repository style.
Do not introduce brittle tests that overfit method-call ordering unless that ordering is itself a framework contract.

PRIORITY ORDER
==================================================
Prioritize uncovered cases with the best ROI, typically:

1. Utility and wait behavior
2. Validation behavior
3. Engine/session-clearing contract behavior
4. Reporting/lifecycle-safe helper behavior
5. Service orchestration only if it can be tested cleanly

IMPLEMENTATION RULES
==================================================
- Work incrementally.
- Make one small safe improvement at a time.
- Reuse existing test infrastructure and naming conventions.
- Keep test classes in the most appropriate existing package.
- Preserve thread-safety and deterministic execution.
- Avoid sleeps and timing-fragile assertions.
- Keep tests isolated and rerunnable.
- Avoid changing production code unless required to support a valid framework contract.

EXECUTION LOOP
==================================================
For each iteration:

1. Analyze one uncovered case.
2. Implement the smallest corresponding test.
3. Run the narrowest relevant Maven test command.
4. Fix the smallest root cause if needed.
5. Re-run the affected tests.
6. Continue until the targeted uncovered cases are addressed.

VALIDATION EXPECTATIONS
==================================================
Validate that:
- existing framework behavior remains intact
- existing class modifications are preserved
- no reporting/lifecycle contract is accidentally changed
- tests remain deterministic
- tests fit the framework’s existing architectural direction

FINAL OUTPUT
==================================================
At the end provide:
- uncovered cases addressed
- tests created or updated
- production files modified, if any
- Maven commands run
- final test results
- risks or remaining gaps
- recommended next incremental coverage targets
