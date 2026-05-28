# UI Recording To ATF Tests Prompt

Reusable Codex/MCP prompt template for converting recorded UI behavior into ATF-compliant tests while preserving the existing framework layers, reporting lifecycle, and screenshot contracts.

You are continuing work inside an EXISTING enterprise-grade Java Automation Testing Framework (ATF).



The current repository structure, framework architecture, lifecycle orchestration, reporting system, and execution model are the SOURCE OF TRUTH.



Do NOT invent a parallel framework.

Do NOT redesign the architecture.

Do NOT replace existing framework patterns.



The goal is to EXTEND and STRENGTHEN the current framework incrementally while preserving behavior and compatibility.



CURRENT FRAMEWORK CHARACTERISTICS

==================================================

The framework already contains:

\- TestNG lifecycle

\- BaseTestUI

\- DriverManager

\- PlaywrightEngine

\- Selenium support

\- Allure reporting

\- Extent reporting

\- ScreenshotManager

\- FailureAttachmentRegistry

\- reporting lifecycle orchestration

\- thread-aware execution

\- UI/API separation

\- reporting contract tests

\- final screenshot support

\- Playwright recorded flow examples

\- reusable framework utilities



Existing framework behavior is considered stable and must be preserved.



REFERENCE FLOW

==================================================

Important:

\- com.endava.ai.ui.tests.Example is ONLY a behavioral reference generated from Playwright recording.

\- RegistrationTests is the preferred framework style and architecture reference.



Reuse the framework completely:

\- BaseTestUI

\- DriverManager

\- PlaywrightEngine

\- Selenium infrastructure

\- reporting

\- screenshot lifecycle

\- logging

\- validations

\- waits

\- services

\- page objects

\- utils

\- factories

\- lifecycle hooks

\- reporting contracts



GOAL

==================================================

Transform the Example flow into a production-grade UI automation suite with:



1\. Broad positive coverage

2\. Broad negative coverage

3\. Stable reporting integration

4\. Stable screenshot lifecycle

5\. Cross-engine compatibility

6\. Cross-reporting compatibility

7\. Reusable architecture

8\. Deterministic execution

9\. AI-friendly observability



ARCHITECTURE RULES

==================================================

Keep tests under:

src/test/java/com/endava/ai/ui/tests



Keep reusable UI/business logic under:

src/main/java/com/endava/ai/ui



STRICTLY reuse existing:

\- pages

\- services

\- validation utilities

\- reporting

\- logging

\- waits

\- screenshot handling

\- factories

\- listeners

\- configuration

\- lifecycle orchestration



Extend the framework consistently instead of introducing duplicate abstractions.



DO NOT:

\- create framework-v2 abstractions

\- create parallel execution models

\- create duplicate screenshot pipelines

\- introduce speculative architecture

\- rewrite the framework



TESTING GOALS

==================================================

Generate multiple focused business-oriented tests instead of recorder-style flows.



Create meaningful positive and negative scenarios.



POSITIVE COVERAGE:

\- successful registration

\- successful login after registration

\- profile validation

\- password change

\- login with changed password

\- account persistence validation

\- navigation validation

\- session persistence validation



NEGATIVE COVERAGE:

\- duplicate email validation

\- invalid email validation

\- weak password validation

\- invalid password policy validation

\- invalid current password

\- invalid login

\- empty required fields

\- invalid postal code

\- invalid phone

\- invalid country/state combinations

\- invalid form state validation

\- validation message assertions

\- boundary conditions



CODE ORGANIZATION RULES

==================================================

\- Split flows into focused tests.

\- Group tests logically into dedicated test classes.

\- Reuse helper methods.

\- Reuse assertions.

\- Reuse validations.

\- Centralize locators ONLY in Page Objects.

\- Create reusable data builders/factories.

\- Keep utilities under src/main.

\- Prefer business-oriented method names.



PAGE OBJECT RULES

==================================================

Use Page Object Model strictly.



Preferred locator strategy order:

1\. data-test

2\. label

3\. role

4\. placeholder

5\. text only as last resort



Avoid:

\- brittle CSS selectors

\- deep DOM traversal

\- recorder-generated selectors

\- duplicated locators



TECHNICAL RULES

==================================================

1\. Generate unique emails automatically.

2\. Avoid flaky waits.

3\. Avoid magic sleeps.

4\. Use Playwright assertions whenever possible.

5\. Keep browser visible by default.

6\. Keep tests isolated and rerunnable.

7\. Avoid click-spam recorder flows.

8\. Preserve existing reporting integration.

9\. Preserve existing logging style.

10\. Preserve existing screenshot lifecycle.



FINAL SCREENSHOT / REPORTING REQUIREMENTS

==================================================

The framework already supports:

\- final-state screenshots

\- failure screenshots

\- Allure reporting

\- Extent reporting



Reuse the existing reporting lifecycle completely.



Every successful UI test should:

\- automatically attach the configured final-state screenshot

\- preserve existing failure screenshot behavior

\- avoid duplicate screenshots

\- preserve reporting lifecycle ordering



Do NOT introduce:

\- duplicate screenshot systems

\- competing attachment flows

\- reporting timing changes



REPORTING CONTRACT REQUIREMENTS

==================================================

Treat reporting behavior as a stable contract.



New tests should validate:

\- final screenshot presence

\- screenshot uniqueness

\- failure screenshot preservation

\- reporting lifecycle consistency

\- Allure/Extent parity

\- Selenium/Playwright parity



Prefer reusable reporting assertions/utilities over adapter-specific assertions spread across tests.



VALIDATION MATRIX REQUIREMENTS

==================================================

Continuously validate combinations of:



ui.engine:

\- selenium

\- playwright



reporting.engine:

\- allure

\- extent



ui.screenshots.enabled:

\- true

\- false



ui.screenshots.capture.final.state:

\- true

\- false



ui.screenshots.on.failure.only:

\- true

\- false



Treat every combination as a supported framework contract.



DETERMINISTIC EXECUTION RULES

==================================================

\- Avoid tests depending on execution order.

\- Avoid shared mutable state.

\- Avoid hidden singleton coupling.

\- Avoid timing-based assertions.

\- Prefer deterministic reporting assertions.

\- Ensure tests are parallel-safe where current infrastructure supports it.



LIFECYCLE OWNERSHIP RULES

==================================================

Do NOT:

\- change driver ownership lifecycle

\- move teardown responsibility

\- alter listener execution order

\- change reporting flush timing

\- change reporting orchestration ownership



Preserve:

\- BaseTestUI ownership

\- TestListener lifecycle

\- ScreenshotManager ownership

\- FailureAttachmentRegistry behavior



SELF-HEALING / AUTONOMOUS EXECUTION

==================================================

When tests fail:

\- inspect the exact failure

\- inspect screenshots

\- inspect reporting artifacts

\- inspect DOM state

\- inspect Playwright traces if available

\- inspect logs if available

\- repair selectors automatically

\- repair waits automatically

\- repair assertions automatically

\- rerun failing tests

\- continue iterating autonomously until green or until a real blocker exists



EXECUTION RULES

==================================================

\- Run mvn test continuously during implementation.

\- Fix compile/runtime/test failures automatically.

\- Do NOT stop after first failure.

\- Continue incremental autonomous iteration.

\- Re-run unstable tests multiple times for stability verification.

\- Validate reporting artifacts after meaningful changes.



INCREMENTAL IMPLEMENTATION STRATEGY

==================================================

Work incrementally.



Required loop:

1\. Analyze existing implementation.

2\. Identify the smallest safe improvement.

3\. Implement one small localized change.

4\. Compile and run the narrowest relevant tests.

5\. Validate:

&#x20;  - reporting

&#x20;  - screenshots

&#x20;  - lifecycle

&#x20;  - engine compatibility

&#x20;  - adapter compatibility

6\. Fix the smallest root cause if something fails.

7\. Continue iterating.



Avoid large rewrites.



TEST QUALITY RULES

==================================================

Prefer:

\- reusable assertions

\- reusable reporting checks

\- reusable validation helpers

\- reusable data builders



Avoid:

\- duplicated assertions

\- duplicated reporting logic

\- duplicated screenshot verification

\- hardcoded timing

\- fragile selectors



AI / MCP FUTURE COMPATIBILITY

==================================================

Implement new tests/utilities in a way that supports future:

\- MCP integrations

\- AI artifact analysis

\- autonomous debugging

\- intelligent retries

\- structured reporting events

\- execution observability

\- artifact normalization

\- failure summarization



FINAL VALIDATION

==================================================

Before finishing:



1\. Run the relevant Maven suites.

2\. Run Selenium coverage.

3\. Run Playwright coverage.

4\. Run Allure reporting validation.

5\. Run Extent reporting validation.

6\. Re-run unstable tests multiple times.

7\. Verify screenshot contracts.

8\. Verify no duplicate screenshots.

9\. Verify teardown safety.

10\. Verify reporting lifecycle consistency.



FINAL OUTPUT

==================================================

At the end provide:

\- tests created

\- files created/modified

\- reporting validations performed

\- screenshot validations performed

\- failures fixed

\- selector repairs performed

\- lifecycle fixes performed

\- test commands run

\- final Maven results

\- remaining flaky/risky areas

\- recommended next architectural improvements

