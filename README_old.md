# Unified UI + API Test Automation Framework

This repository contains a **production‑grade UI and API test automation framework** designed around **semantic steps**, **strict ownership**, and **deterministic reporting**.

The framework is intentionally opinionated: it enforces clear boundaries between tests, workflows, engines, and reporting so that failures are predictable, reports are readable, and the system can be extended or regenerated (even by an AI) without ambiguity.

---

## Why This Framework Exists

Traditional test frameworks tend to:
- mix engine logic into tests,
- duplicate failures and stacktraces,
- blur ownership between tests, steps, and reporting,
- become fragile when engines or reporting tools change.

This framework solves those problems by enforcing:
- **one test lifecycle**
- **one failure source per step**
- **one place for screenshots**
- **one semantic model for UI and API**

---

## Core Principles

- **Tests orchestrate flows**, nothing more.
- **Services define workflows**, not assertions.
- **Validations define assertions**, not execution.
- **Engines execute**, they do not log or fail tests.
- **Reporting is centralized and deterministic.**

---

## Project Structure

```
src/main/java/com/endava/ai
├── ui
│   ├── engine        # UIEngine abstraction + implementations
│   ├── actions       # UIActions (click, type, navigation)
│   ├── utils         # Wait and sync utilities
│   ├── core          # BaseTestUI, DriverManager, TestListener
│   └── tests         # UI tests (orchestration only)
│
├── api
│   ├── client        # ApiClient (HTTP execution only)
│   ├── service       # API workflows
│   ├── validation    # API assertions
│   └── tests         # API tests (orchestration only)
│
└── core
    └── reporting
        ├── StepLogger
        ├── ReportingManager
        ├── ReportLogger
        └── adapters  # Extent / Allure
```

---

## Test Design Rules

- **Tests**
  - Orchestrate workflows
  - Do not call engines
  - Do not assert
  - Do not log

- **Services**
  - Define workflows only
  - No assertions
  - No engine knowledge

- **Validations**
  - Assertions only
  - No execution logic

- **UIEngine / ApiClient**
  - Execution only
  - No reporting
  - No FAIL logic

---

## UI Engine Contract

`UIEngine` defines the **minimal contract** for executing UI actions.

- Supported operations: `click`, `type`, `getText`, `wait`
- No assertions
- No reporting
- No failure decisions

Engines are interchangeable (e.g. Selenium, Playwright) without changing tests.

---

## API Client Contract

`ApiClient` is responsible **only** for executing HTTP requests.

- Executes requests
- Returns responses
- No assertions
- No reporting

Workflow logic belongs in Services; assertions belong in Validations.

---

## Reporting Architecture

### Core Components

- **TestListener**
  - Owns the test lifecycle
  - Creates and closes tests
  - Captures screenshots on test failure (UI only)

- **StepLogger**
  - Owns semantic steps
  - Owns step‑level failures
  - Prevents duplicate failures

- **ReportingManager**
  - Resolves the active reporting adapter
  - Exposes a unified `ReportLogger`

---

## Logging & Step Semantics (Canonical Rules)

- Every semantic step **MUST** be created via `StepLogger.startStep(...)`
- `StepLogger` is the **only** caller of `ReportLogger.fail(...)`
- Exactly **one FAIL per step**
- `logDetail(...)` **MUST NOT** be used outside an active step
- Stacktrace is rendered **once**, as a **monospace code block**, in the failing step
- No logging or FAIL outside steps
- Any exception outside a step results in **TEST‑level FAIL**
- Screenshot is captured **only by TestListener**, **once per test FAIL**, **UI only**

These rules are non‑negotiable and enforced by design.

---

## Test Lifecycle

- A **single TestListener** controls the lifecycle for both UI and API tests.
- Tests start and end exactly once.
- Steps exist only within an active test.
- Late or leaked steps are rejected.

---

## Screenshot Rules

- UI only
- Exactly one per failed test
- Attached at **test level**, never at step level
- Captured before test completion

---

## UI Tests

- Tests orchestrate workflows
- UIActions execute interactions
- UIEngine performs execution
- Steps are logged semantically

---

## API Tests

- Tests orchestrate API workflows
- Services execute HTTP requests
- Validations assert responses in separate steps

### API Step Naming

- **HTTP steps:** `HTTP {METHOD} {relativePath}`
- **Validation steps:** `Validate: {expectations}`

Request and response payloads are logged as code blocks.

---

## Reporting UX Model

### Test Node
- Test name
- Final status
- Screenshot (only on FAIL)

### Step Node
- Semantic title
- Short standardized details
- Payloads and stacktrace as code blocks
- PASS / FAIL at step level

---

## Threading Model

- Test context
- UIEngine
- Reporting context

All are managed via **ThreadLocal**, ensuring isolation and safe parallel execution.

---

## Configuration

Configuration is done via `framework.properties`:

```properties
reporting.engine=extent | allure
reporting.mode=shared | local

base.url=https://example.com
base.url.api=https://api.example.com
```

- `shared` reuses the reporting model and adapter
- Reporting lifecycle is always single

---

## TestNG Listener Order

Listener order is critical:

```xml

<listeners>
    <listener class-name="io.qameta.allure.testng.AllureTestNg"/>
    <listener class-name="com.endava.ai.core.listener.TestListener"/>
</listeners>
```

The reporting engine listener must be registered **before** the framework listener.

---

## Common Anti‑Patterns (Do Not Do This)

- Logging or failing from UIEngine or ApiClient
- Assertions inside Services
- Screenshot inside steps
- Multiple FAILs for a single step
- Stacktrace at test level
- Direct engine calls in tests

---

## Extensibility

You can safely:
- add a new UI engine
- add a new reporting adapter
- extend API workflows
- add new validations

As long as you respect the contracts above, no existing tests need to change.

---

## Final Note

This framework is intentionally strict.

That strictness is what makes:
- reports predictable,
- failures readable,
- automation scalable,
- and code generation (even by AI) reliable.
