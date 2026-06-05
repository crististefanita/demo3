# UI Recording To ATF Tests Prompt

Reusable Codex/MCP prompt for converting a recorded Playwright flow into ATF-compliant UI tests inside this repository.

Use this prompt when a raw UI recording exists and must be transformed into maintainable framework tests without rewriting the framework.

SOURCE OF TRUTH
==================================================

You are working inside an EXISTING enterprise-grade Java Automation Testing Framework.

The current repository is the source of truth:

- framework structure
- TestNG lifecycle
- `BaseTestUI`
- `DriverManager`
- Selenium / Playwright engines
- reporting lifecycle
- screenshot lifecycle
- listeners
- waits
- services
- validations
- page objects
- factories
- existing conventions

Do NOT:

- invent framework-v2 abstractions
- redesign the architecture
- replace framework patterns
- create duplicate reporting or screenshot pipelines

Extend the current framework carefully and incrementally.

MAIN FOCUS
==================================================

The PRIMARY behavioral source is:

- `src/test/java/com/endava/ai/ui/tests/Example.java`

Treat `Example` as the flow that must be understood, decomposed, and transformed into proper ATF tests.

The PRIMARY target location for the resulting business tests is:

- `src/test/java/com/endava/ai/ui/tests`

The SECONDARY style reference is:

- `RegistrationTests`

Use `RegistrationTests` mainly as a framework-style example, not as the main functional target.

In short:

1. understand the business flow from `Example`
2. split it into focused tests
3. place those focused UI tests under `com.endava.ai.ui.tests`
4. add reusable support under `src/main/java/com/endava/ai/ui`

GOAL
==================================================

Transform the recorded flow into a production-grade UI suite with:

- broad positive coverage
- broad negative coverage
- reusable page/service/validation layers
- stable reporting integration
- stable screenshot lifecycle
- cross-engine compatibility
- deterministic execution
- AI-friendly observability

Do not optimize for raw test count.

Optimize for a stronger UI automation core and stronger business coverage.

ARCHITECTURE RULES
==================================================

Keep:

- business UI tests under `src/test/java/com/endava/ai/ui/tests`
- reusable UI logic under `src/main/java/com/endava/ai/ui`

Strictly reuse existing:

- pages
- services
- validations
- logging
- waits
- factories
- configuration
- listeners
- screenshot handling
- reporting lifecycle

Do not create parallel abstractions when an existing layer can be extended.

COVERAGE TARGETS
==================================================

Use the recorded flow to derive meaningful focused tests.

Positive targets:

- successful registration
- successful login after registration
- profile validation
- password change
- login with changed password
- account persistence validation
- navigation validation
- session persistence validation
- protected route behavior for authenticated users

Negative targets:

- duplicate email validation
- invalid email validation
- weak password validation
- invalid password policy validation
- invalid current password
- invalid login
- empty required fields
- invalid phone
- invalid form state validation
- validation message assertions
- protected route behavior for unauthenticated users
- boundary conditions where the live UI supports them clearly

Do NOT force speculative negatives if the live application does not support a stable contract for them.

PAGE OBJECT / TEST DESIGN RULES
==================================================

Use Page Object Model strictly.

Preferred locator strategy order:

1. `data-test`
2. label
3. role
4. placeholder
5. text only as last resort

Test design rules:

- split recorder flows into focused tests
- reuse helper methods
- reuse assertions
- reuse validations
- centralize locators in page objects
- keep utilities under `src/main`
- prefer business-oriented method names

Avoid:

- recorder-generated selector noise
- deep DOM traversal
- duplicated locators
- giant end-to-end test monsters
- duplicated assertions across tests

TECHNICAL RULES
==================================================

- generate unique emails automatically
- avoid magic sleeps
- avoid flaky waits
- keep browser visible by default unless current config says otherwise
- keep tests isolated and rerunnable
- preserve existing reporting integration
- preserve existing screenshot lifecycle
- preserve existing listener ownership

ITERATIVE EXECUTION MODEL
==================================================

Work in small safe iterations.

Required loop:

1. inspect `Example`
2. inspect existing UI framework code
3. inspect the live UI if needed
4. identify the weakest useful gap
5. implement the smallest safe improvement
6. run the narrowest relevant Maven validation
7. inspect failures, screenshots, logs, and DOM behavior
8. fix the smallest real root cause
9. reassess coverage
10. continue iterating

Do NOT stop at the first green run.

A passing narrow suite is only a checkpoint.

If an in-scope area can still be improved safely, continue.

FUNCTIONAL COVERAGE EVALUATION
==================================================

After each meaningful validation pass, reassess coverage across:

- registration flow
- login flow
- profile flow
- password change flow
- navigation / route protection
- validation feedback quality
- negative scenario depth
- deterministic behavior
- Selenium compatibility
- Playwright compatibility
- reporting confidence
- screenshot lifecycle confidence

Treat low-scoring but safely improvable areas as unfinished work.

REPORTING AND SCREENSHOT CONTRACTS
==================================================

Preserve the existing reporting lifecycle completely.

New tests must not:

- create duplicate screenshot systems
- create competing attachment flows
- change reporting timing
- change teardown ownership

Validate where relevant:

- final screenshot presence
- failure screenshot preservation
- screenshot uniqueness
- reporting lifecycle consistency
- Selenium / Playwright parity
- Extent / Allure parity

FINAL VALIDATION
==================================================

Before finishing:

1. run the relevant Maven suites
2. run the affected business tests under `com.endava.ai.ui.tests`
3. validate Selenium coverage
4. validate Playwright coverage where practical
5. validate Extent behavior where practical
6. validate Allure behavior where practical
7. verify screenshot behavior remains compatible
8. verify teardown/session isolation
9. reassess coverage gaps
10. repeat if meaningful safe improvements still remain

FINAL OUTPUT
==================================================

Provide:

- tests created or updated under `com.endava.ai.ui.tests`
- support files created or updated under `src/main/java/com/endava/ai/ui`
- behavior extracted from `Example`
- coverage added
- reporting validations performed
- screenshot validations performed
- engine validations performed
- failures fixed
- selector or wait repairs performed
- lifecycle or isolation fixes performed
- Maven commands run
- final Maven results
- remaining flaky / risky / unsupported areas
- recommended next step

FINAL SELF-ASSESSMENT
==================================================

At the end, provide a score table:

| Area | What was verified | Score | Risk / Note |
|---|---|---:|---|

Include at least:

- `Example` flow coverage
- registration coverage
- login coverage
- profile coverage
- password change coverage
- negative scenario coverage
- validation message quality
- reusable service/page/validation design
- deterministic execution
- Selenium coverage
- Playwright coverage
- Extent validation
- Allure validation
- screenshot lifecycle confidence
- reporting lifecycle confidence
- maintainability
- AI/MCP readiness

FINALIZATION GUARD
==================================================

Do not claim completion merely because:

- code compiles
- one enhancement was implemented
- one suite passed

Completion requires both:

- passing validation
- no remaining obvious safe iterative improvements in scope

If multiple in-scope areas still score below 9/10, assume more iteration is expected unless clearly blocked.

If something was not verified, say it explicitly.
