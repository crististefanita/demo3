# API Framework Evolution Prompt

Reusable Codex/MCP prompt template for iterative API-focused evolution inside the existing ATF. Use it when API coverage, validators, contracts, diagnostics, or reporting-aware API behavior must improve without changing the established framework architecture.

You are continuing evolution of an EXISTING enterprise-grade Java Automation Testing Framework (ATF).



The repository, framework architecture, lifecycle orchestration, reporting system, execution model, utilities, services, validations, schemas, factories, existing API tests, and reporting contracts are the SOURCE OF TRUTH.



IMPORTANT:

This is NOT a greenfield project.

This is NOT a framework rewrite task.



Your role is to EVOLVE the framework carefully, incrementally, and intelligently.



DO NOT:

\- invent a parallel framework

\- redesign the architecture

\- replace existing framework patterns

\- introduce framework-v2 abstractions

\- introduce speculative architecture

\- bypass existing utilities/services

\- replace reporting engines

\- rewrite lifecycle orchestration



PRIMARY STRATEGIC GOAL

==================================================

The framework already contains strong API automation coverage.



The next objective is NOT blindly adding more tests.



The next objective is evolving the framework into a more:

\- semantic

\- contract-driven

\- maintainable

\- observable

\- reusable

\- AI-friendly

\- diagnostics-oriented

\- reporting-intelligent

API automation platform.



PRIORITY ORDER

==================================================

Always prioritize in this order:



1\. Preserve existing contracts

2\. Preserve reporting behavior

3\. Preserve deterministic execution

4\. Preserve API/UI isolation

5\. Improve semantic clarity

6\. Improve maintainability

7\. Reduce duplication

8\. Improve diagnostics

9\. Improve reusable validations/helpers

10\. Expand valuable coverage



IMPORTANT SCOPE

==================================================

This task is STRICTLY focused on API automation.



UI tests and UI framework behavior MUST remain unchanged.



DO NOT:

\- generate UI tests

\- modify existing UI tests

\- change Playwright behavior

\- change Selenium behavior

\- initialize browsers in API tests

\- introduce screenshots/browser logic into API flows

\- introduce UI lifecycle assumptions

\- introduce UI dependencies into API execution



UI tests are OUT OF SCOPE.



TARGET API

==================================================

Use the existing framework together with:



https://gorest.co.in



IMPORTANT:

Do NOT treat GoRest only as a static endpoint list.



Act agentically:

\- inspect the GoRest website/documentation

\- inspect live API behavior

\- inspect endpoint relationships

\- inspect nested resources

\- inspect pagination/filtering/sorting

\- inspect validation behavior

\- inspect auth behavior

\- inspect real error contracts

\- inspect rate-limit behavior carefully

\- inspect inconsistencies between docs and live API



Explore and understand:

\- users

\- posts

\- comments

\- todos

\- nested relationships

\- lifecycle relationships

\- validation rules

\- response patterns



Use discoveries to improve:

\- coverage

\- contracts

\- validators

\- diagnostics

\- reporting semantics

\- reusable helpers



SOURCE OF TRUTH PRIORITY

==================================================

Use this priority order for implementation decisions:



1\. Existing repository architecture

2\. Existing framework conventions

3\. Existing tests and validators

4\. Existing reporting contracts

5\. Verified live API behavior

6\. GoRest documentation/examples

7\. Reasonable inference



Never treat inferred behavior as verified behavior.



REUSE EXISTING FRAMEWORK

==================================================

Strictly reuse existing:

\- API services

\- request builders

\- response validators

\- reporting

\- logging

\- configuration

\- auth handling

\- assertions

\- lifecycle hooks

\- factories

\- schemas

\- serializers/deserializers

\- cleanup patterns

\- test structure conventions



Preserve:

\- reporting lifecycle

\- StepLogger behavior

\- Allure behavior

\- Extent behavior

\- API logging behavior

\- existing validators

\- existing assertions

\- existing cleanup strategies



DO NOT:

\- create a parallel API client

\- create parallel reporting

\- create giant abstractions

\- create magic DSLs

\- introduce event buses

\- introduce orchestration frameworks



ARCHITECTURE RULES

==================================================

Keep API tests under:

src/test/java/com/endava/ai/api/tests



Keep reusable API/business logic under:

src/main/java/com/endava/ai/api



Follow REAL repository conventions.

Do NOT invent new conventions unnecessarily.



STRATEGIC FOCUS AREAS

==================================================



1\. CONTRACT QUALITY

\--------------------------------------------------

Strengthen:

\- status validation

\- schema validation

\- error validation

\- relationship validation

\- pagination validation

\- filtering validation

\- serialization consistency

\- content-type validation

\- response consistency



2\. SEMANTIC EXECUTION CLARITY

\--------------------------------------------------

Improve semantic readability of:

\- reporting

\- logs

\- validators

\- assertions

\- API intent

\- resource lifecycle flows



Prefer explicit semantic intent:

\- CREATE\_RESOURCE

\- VALIDATE\_SCHEMA

\- NEGATIVE\_CONTRACT

\- RELATIONSHIP\_VALIDATION

\- PAGINATION\_VALIDATION

\- AUTH\_VALIDATION



IMPORTANT:

Keep implementation lightweight.

Avoid overengineering.



3\. REPORTING INTELLIGENCE

\--------------------------------------------------

Improve:

\- semantic reporting clarity

\- validation traceability

\- failure diagnostics

\- contract visibility

\- nested-resource visibility

\- request/response diagnostics



Reuse the existing reporting lifecycle completely.



Do NOT replace:

\- Extent

\- Allure

\- StepLogger

\- reporting orchestration



4\. MAINTAINABILITY

\--------------------------------------------------

Before adding new tests:

\- detect duplicated validations

\- detect duplicated assertions

\- detect duplicated builders

\- detect duplicated pagination checks

\- detect duplicated relationship checks

\- detect repeated error validations



Prefer:

\- consolidation

\- reusable validators

\- reusable helpers

\- reusable diagnostics

over raw test count expansion.



5\. API OBSERVABILITY

\--------------------------------------------------

Improve:

\- failure diagnostics

\- validator diagnostics

\- request/response visibility

\- contract mismatch clarity

\- retry diagnostics

\- rate-limit diagnostics

\- semantic logging



6\. AI / MCP READINESS

\--------------------------------------------------

Prepare the framework for future:

\- MCP integrations

\- AI-assisted analysis

\- semantic execution tracing

\- structured diagnostics

\- intelligent debugging

\- execution observability



Focus on:

\- semantic metadata

\- deterministic diagnostics

\- explicit validation intent

\- reusable contract helpers

\- reporting traceability



Keep implementation incremental and lightweight.



API COVERAGE GOALS

==================================================

Expand realistic and valuable coverage only where meaningful.



Positive coverage:

\- create resource

\- get resource

\- update resource

\- delete resource

\- collection/list validation

\- pagination validation

\- filtering validation

\- sorting validation

\- nested relationships

\- persistence validation

\- schema validation

\- serialization/deserialization validation



Negative coverage:

\- invalid payloads

\- missing required fields

\- auth failures

\- invalid relationships

\- malformed requests

\- invalid IDs

\- invalid query params

\- duplicate resources where meaningful

\- boundary conditions

\- error contracts

\- rate-limit handling if safely testable



TEST DESIGN RULES

==================================================

\- Split flows into focused tests

\- Group tests logically

\- Prefer reusable assertions

\- Prefer reusable validators

\- Prefer reusable diagnostics

\- Prefer business-oriented naming

\- Prefer deterministic assertions

\- Avoid giant end-to-end API monsters

\- Avoid duplicated validation logic

\- Avoid brittle assumptions

\- Avoid hardcoded timing



DETERMINISTIC EXECUTION RULES

==================================================

\- Avoid execution-order dependency

\- Avoid shared mutable state

\- Avoid hidden singleton coupling

\- Preserve parallel-safety where supported

\- Clean up created resources when possible

\- Prefer deterministic data generation



SELF-HEALING / AUTONOMOUS EXECUTION

==================================================

When failures happen:

\- inspect request payloads

\- inspect response payloads

\- inspect headers/status codes

\- inspect reporting artifacts

\- inspect validators

\- inspect duplicated logic

\- inspect semantic inconsistencies

\- inspect live API behavior

\- compare docs vs live behavior

\- repair smallest root cause

\- rerun affected suites

\- continue iterating autonomously



Do NOT hide:

\- flaky behavior

\- inconsistent live behavior

\- rate-limit instability

\- contract inconsistencies



Document them explicitly.



INCREMENTAL IMPLEMENTATION STRATEGY

==================================================

Mandatory iterative loop:



1\. Analyze existing API implementation

2\. Analyze existing API tests

3\. Analyze existing validators/builders/reporting

4\. Inspect GoRest docs and live behavior

5\. Detect:

&#x20;  - missing coverage

&#x20;  - duplicated logic

&#x20;  - weak contracts

&#x20;  - weak diagnostics

&#x20;  - weak reporting semantics

6\. Propose smallest safe improvement

7\. Implement incrementally

8\. Run narrow Maven suites

9\. Validate:

&#x20;  - API behavior

&#x20;  - reporting behavior

&#x20;  - serialization

&#x20;  - validators

&#x20;  - deterministic execution

&#x20;  - API/UI isolation

10\. Fix smallest root cause

11\. Continue iteratively



Avoid large rewrites.



EXECUTION RULES

==================================================

\- Run Maven tests continuously

\- Prefer narrow suites first

\- Run broader suites after meaningful changes

\- Automatically fix compile/runtime/test failures

\- Do NOT stop at first failure

\- Re-run unstable suites

\- Validate reporting behavior after changes



Existing tests are contracts.



Any regression introduced into existing API tests is considered a real regression until proven otherwise.



TRACEABILITY REQUIREMENTS

==================================================

For every significant enhancement explain explicitly:



\- what was derived from repository conventions

\- what was derived from existing tests

\- what was derived from existing validators/builders

\- what was derived from GoRest documentation

\- what was derived from live API behavior

\- what assumptions were made

\- what inconsistencies were discovered

\- what validations were reused

\- what validations were newly introduced



Do NOT present inferred behavior as verified behavior.



If:

\- docs

\- live API

\- examples

\- repository behavior



are inconsistent,

explicitly document the inconsistency.



FINAL VALIDATION

==================================================

Before finishing:



1\. Run relevant Maven suites

2\. Verify existing API tests still pass

3\. Verify UI tests remain untouched

4\. Verify reporting behavior remains stable

5\. Verify deterministic execution

6\. Verify validators remain backward compatible

7\. Verify no API/UI cross-contamination

8\. Verify semantic/reporting improvements remain backward compatible



FINAL OUTPUT

==================================================

Generate a concise but structured implementation report including:



\- tests added

\- files modified

\- endpoints/resources covered

\- reusable helpers introduced

\- duplicated logic reduced

\- semantic/reporting improvements added

\- diagnostics improvements added

\- validators standardized

\- validations reused

\- validations newly introduced

\- repository patterns reused

\- existing tests used as references

\- GoRest docs/pages inspected

\- live API behaviors validated

\- inconsistencies discovered

\- reporting validations performed

\- API/UI isolation checks performed

\- failures fixed

\- request/response fixes

\- serialization fixes

\- Maven commands run

\- final Maven results

\- assumptions made

\- remaining risky/flaky/unverified areas

\- recommended next architectural evolution steps



FINAL SELF-VALIDATION AND ANALYSIS TASK

==================================================



Before finishing, perform a final verification pass over your own changes.



1\. Run validation

\- Run the narrowest relevant API suites first.

\- Run all affected API tests.

\- Run existing API regression tests.

\- Run broader Maven validation if practical.

\- Re-run any unstable/failing test at least once.

\- Do not ignore failures.



2\. Verify scope

Confirm explicitly:

\- no UI tests were added

\- no UI tests were modified

\- no Playwright/Selenium behavior was changed

\- no browser dependency leaked into API flows

\- existing API tests still pass

\- reporting behavior remains stable



3\. Verify quality

Analyze your changes for:

\- duplicated assertions

\- duplicated validators

\- duplicated request builders

\- weak contracts

\- brittle assumptions

\- shared mutable state

\- execution-order dependency

\- missing cleanup

\- weak diagnostics

\- reporting gaps



4\. Verify reporting artifacts

Inspect generated reports/logs where available and confirm:

\- API steps are readable

\- request/response logging is preserved

\- failures would be diagnosable

\- semantic intent is visible

\- no UI screenshots appear in API tests



5\. Produce final analysis report

At the end, provide a concise table with scores from 1–10:



| Area | What was verified | Score | Risk / Note |

|---|---|---:|---|



Include at least:

\- API coverage

\- contract quality

\- negative scenarios

\- validator reuse

\- reporting quality

\- diagnostics quality

\- deterministic execution

\- cleanup strategy

\- API/UI isolation

\- maintainability

\- traceability

\- GoRest docs/live behavior usage

\- regression safety

\- AI/MCP readiness



6\. Final verdict

End with:

\- overall score

\- strongest improvements

\- remaining risks

\- recommended next step



Do not claim success unless Maven commands actually passed.

If something was not verified, say it explicitly.

