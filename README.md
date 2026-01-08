
## 1. Purpose of This Repository

This repository represents a **reference implementation** of the *Living Architecture* concept, applied to a **UI & API–centric system**.

Its purpose is to:
- demonstrate how architectural principles translate into code
- serve as a **living reference**, not a static example
- align documentation, contracts, and implementation into a single source of truth

---

## 2. Living Architecture – Core Definition

Living Architecture treats software architecture as:

- continuously evolving
- contract-driven
- observable
- resilient to change

Architecture is **not frozen** at design time; instead, it adapts through:
- explicit contracts
- versioned APIs
- externalized behavior definitions

---

## 3. Architectural Principles (As Implemented)

### 3.1 Contract-First Architecture

Contracts are defined **before and independently of implementation**.

In this repository:
- JSON files represent executable contracts
- Java code implements these contracts
- Tests enforce contract compliance

Contracts are:
- explicit
- versionable
- consumer-facing

---

### 3.2 Separation of Concerns

The system is decomposed along **clear responsibility boundaries**:

| Layer | Responsibility |
|-----|---------------|
| UI | Interaction, orchestration, error presentation |
| API | Contract exposure, validation, authorization |
| Domain | Business rules and invariants |
| Tests | Behavioral truth source |

This separation is preserved in both documentation and code structure.

---

### 3.3 Evolution Without Breakage

Change is managed by:
- adding new contracts
- introducing new versions
- never silently modifying existing behavior

Existing contracts remain valid until explicitly deprecated.

---

## 4. Repository Structure (Authoritative)

```
demo4/
├── pom.xml
└── src/
    └── main/
        └── java/
```

Additionally:
- JSON files define request/response expectations
- These JSON files act as **living API specifications**

---

## 5. pom.xml – Architectural Role

The `pom.xml` is not just a build descriptor; it is part of the architecture.

It defines:
- dependency boundaries
- lifecycle phases
- enforcement of consistency

Architectural changes that affect:
- dependencies
- testing strategy
- runtime behavior  
**must be reflected here first**

---

## 6. API Design Model

The API follows these enforced rules:

- explicit resource modeling
- deterministic responses
- consistent error formats
- HTTP semantics respected

The API surface is **defined by contracts**, not by controllers.

---

## 7. JSON Contracts (Critical Component)

### 7.1 Purpose

JSON files define:
- valid inputs
- invalid inputs
- expected outputs
- error structures

They are used for:
- automated testing
- documentation
- regression protection

---

### 7.2 Positive Scenarios

Example:
- `registration_positive.json`

Defines:
- required fields
- valid data combinations
- successful response payload

---

### 7.3 Negative / Validation Scenarios

Example:
- `registration_tests_invalid_email.json`

Defines:
- invalid inputs
- expected validation errors
- stable error semantics

Errors are:
- predictable
- machine-readable
- user-safe

---

## 8. Error Handling Model

Errors are treated as **first-class API responses**, not exceptions.

Each error:
- has a clear cause
- is reproducible via contract
- is observable in logs and tests

UI layers rely on **error stability**, not message parsing.

---