# API Framework Evolution Prompt

Reusable Codex/MCP prompt for evolving the existing Java API automation framework inside this repository.

Use it when API coverage, validators, contracts, diagnostics, reporting clarity, or reusable helpers must improve without changing the established ATF architecture.

You are evolving an EXISTING enterprise-grade Java Automation Testing Framework (ATF).

The repository implementation is the source of truth:

- code in `src/main/java`
- tests in `src/test/java`
- reporting behavior
- lifecycle behavior
- configuration
- schemas
- factories
- existing conventions

This is NOT:

- a greenfield project
- a framework rewrite
- a license to invent framework-v2 abstractions

Your job is to improve the current framework carefully, incrementally, and iteratively.

PRIMARY GOAL
==================================================

Do not optimize for raw test count.

Optimize for a better API automation platform:

- stronger contracts
- better coverage
- clearer reporting
- better diagnostics
- more reusable validators/helpers
- better maintainability
- better AI/MCP readiness

The expected operating model is:

1. inspect the existing API framework
2. inspect the live API and docs
3. find the weakest useful gap
4. implement the smallest safe improvement
5. run the narrowest relevant Maven validation
6. reassess
7. continue iterating until no obvious safe improvement remains

PRIORITY ORDER
==================================================

Always prioritize in this order:

1. preserve existing contracts
2. preserve reporting behavior
3. preserve deterministic execution
4. preserve API/UI isolation
5. improve semantic clarity
6. improve maintainability
7. reduce duplication
8. improve diagnostics
9. improve reusable validations/helpers
10. expand valuable coverage

SCOPE BOUNDARY
==================================================

This prompt is API-only.

UI behavior must remain unchanged.

Do NOT:

- add UI tests
- modify existing UI tests
- change Playwright behavior
- change Selenium behavior
- initialize browsers in API tests
- introduce screenshots/browser logic into API execution
- leak UI lifecycle assumptions into API flows

TARGET API
==================================================

Use the existing framework against:

https://gorest.co.in

Do not treat GoRest as a static endpoint list.

You must inspect:

- documentation
- live behavior
- resource relationships
- nested resources
- pagination
- filtering
- sorting
- validation behavior
- auth behavior
- error contracts
- rate-limit behavior
- docs vs live inconsistencies

Explore and evolve coverage around:

- users
- posts
- comments
- todos
- nested relationships
- lifecycle relationships
- response patterns
- validation rules

SOURCE OF TRUTH PRIORITY
==================================================

Use this order for implementation decisions:

1. existing repository architecture
2. existing framework conventions
3. existing tests and validators
4. existing reporting contracts
5. verified live API behavior
6. GoRest documentation/examples
7. reasonable inference

Never present inferred behavior as verified behavior.

NON-NEGOTIABLE REUSE RULES
==================================================

Strictly reuse existing framework layers where possible:

- `BaseTestAPI`
- API steps
- API services
- `ApiClient`
- `ApiActions`
- validators
- schemas
- configuration
- reporting
- factories
- cleanup patterns

Do NOT:

- create a parallel API client
- create parallel reporting
- create giant speculative abstractions
- introduce orchestration frameworks
- replace Allure / Extent / StepLogger

PACKAGE RULES
==================================================

Keep:

- business API tests under `src/test/java/com/endava/ai/api/tests`
- reusable API logic under `src/main/java/com/endava/ai/api`

Follow repository conventions.
Do not invent new top-level patterns unless clearly justified by existing code style.

STRATEGIC FOCUS AREAS
==================================================

1. Contract quality

Strengthen:

- status validation
- schema validation
- error validation
- relationship validation
- pagination validation
- filtering validation
- sorting validation
- content-type validation
- serialization consistency
- response consistency

2. Semantic execution clarity

Improve readability of:

- test intent
- validators
- reporting
- logs
- API actions
- lifecycle flows

Prefer explicit semantic names such as:

- `CREATE_RESOURCE`
- `VALIDATE_SCHEMA`
- `NEGATIVE_CONTRACT`
- `RELATIONSHIP_VALIDATION`
- `PAGINATION_VALIDATION`
- `AUTH_VALIDATION`

3. Reporting intelligence

Improve:

- semantic reporting clarity
- request/response visibility
- validation traceability
- failure diagnostics
- nested-resource visibility

Preserve the existing reporting lifecycle completely.

4. Maintainability

Before adding tests, look for:

- duplicated assertions
- duplicated validators
- duplicated builders
- duplicated pagination checks
- duplicated relationship checks
- repeated error validations

Prefer consolidation over raw test count.

5. API observability

Improve:

- failure diagnostics
- contract mismatch clarity
- validator diagnostics
- semantic logging
- request/response visibility
- rate-limit diagnostics

6. AI / MCP readiness

Prepare the framework for:

- semantic execution traces
- structured diagnostics
- reusable contract helpers
- easier future agentic extension

API COVERAGE GOALS
==================================================

Aim for realistic, valuable, iterative coverage.

Positive coverage:

- create resource
- get resource
- update resource
- delete resource
- list/collection validation
- pagination validation
- filtering validation
- sorting validation
- nested relationships
- persistence validation
- schema validation
- serialization/deserialization validation

Negative coverage:

- invalid payloads
- missing required fields
- auth failures
- invalid relationships
- malformed requests
- invalid IDs
- invalid query params
- duplicate resources where meaningful
- boundary conditions
- error contracts
- rate-limit handling if safely testable

TEST DESIGN RULES
==================================================

- split flows into focused tests
- keep tests business-readable
- prefer reusable validators/assertions
- prefer deterministic assertions
- avoid giant API end-to-end monsters
- avoid duplicated validation logic
- avoid brittle assumptions
- avoid hardcoded timing

DETERMINISTIC EXECUTION RULES
==================================================

- avoid execution-order dependency
- avoid shared mutable state
- preserve parallel-safety where supported
- clean up created resources when possible
- prefer deterministic data generation

MANDATORY ITERATIVE LOOP
==================================================

You are expected to work in small safe iterations.

Baseline loop:

1. analyze existing API implementation
2. analyze existing API tests
3. analyze validators / builders / reporting
4. inspect GoRest docs and live behavior
5. detect the current weakest useful gap
6. implement the smallest safe improvement
7. run narrow Maven validation
8. inspect failures and reporting artifacts
9. fix the smallest real root cause
10. reassess what still remains weak
11. continue

ITERATION STOP RULE
==================================================

Do NOT stop at the first green run.

A passing narrow suite is only a checkpoint.

After each successful validation pass:

1. reassess the changed area
2. identify the lowest-scoring in-scope category
3. ask whether one more small safe improvement is possible
4. if yes, implement it and rerun validation
5. repeat until:
   - no obvious safe improvement remains, or
   - the remaining gaps are blocked, flaky, out of scope, or too risky

Treat unfinished weak areas as unfinished work unless they are explicitly blocked.

Bias toward one more safe iteration when there is a clear next improvement.

EXECUTION RULES
==================================================

- run Maven tests continuously
- prefer narrow suites first
- run broader suites after meaningful changes
- automatically fix compile/runtime/test failures
- do not stop at first failure
- rerun unstable suites at least once
- validate reporting behavior after changes

Existing tests are contracts.

If tests pass, do one more improvement check across:

- contract quality
- negative coverage
- validator reuse
- diagnostics
- reporting validation

If any of those can be improved safely, continue.

LIVE API DISCIPLINE
==================================================

When docs and live behavior differ:

- trust verified live behavior over examples
- document the inconsistency explicitly
- do not encode fragile assumptions as hard contracts

When failures happen:

- inspect request payloads
- inspect response payloads
- inspect headers and status codes
- inspect validators
- inspect reporting artifacts
- compare docs vs live behavior
- repair the smallest root cause
- rerun the affected suites

Do not hide:

- flaky behavior
- inconsistent live behavior
- rate-limit instability
- contract inconsistencies

FINAL VALIDATION
==================================================

Before finishing:

1. run relevant Maven suites
2. verify affected API tests still pass
3. verify UI tests remain untouched
4. verify reporting behavior remains stable
5. verify deterministic execution
6. verify validators remain backward compatible
7. verify no API/UI cross-contamination

FINAL OUTPUT
==================================================

Produce a concise structured report that includes:

- tests added or updated
- files modified
- endpoints/resources covered
- reusable helpers introduced
- duplicated logic reduced
- semantic/reporting improvements added
- diagnostics improvements added
- validators standardized
- validations reused
- validations newly introduced
- repository patterns reused
- GoRest docs/pages inspected
- live API behaviors validated
- inconsistencies discovered
- reporting validations performed
- API/UI isolation checks performed
- Maven commands run
- final Maven results
- assumptions made
- remaining risky/flaky/unverified areas
- recommended next step

FINAL SELF-ASSESSMENT
==================================================

At the end, provide a score table:

| Area | What was verified | Score | Risk / Note |
|---|---|---:|---|

Include at least:

- API coverage
- contract quality
- negative scenarios
- validator reuse
- reporting quality
- diagnostics quality
- deterministic execution
- cleanup strategy
- API/UI isolation
- maintainability
- traceability
- GoRest docs/live behavior usage
- regression safety
- AI/MCP readiness

FINALIZATION GUARD
==================================================

Do not claim completion merely because:

- code compiles
- one enhancement was implemented
- the first affected suite passed

Completion requires both:

- passing validation
- no remaining obvious safe iterative improvements in scope

If multiple in-scope areas still score below 9/10, assume more iteration is expected unless clearly blocked.

If something was not verified, say it explicitly.
