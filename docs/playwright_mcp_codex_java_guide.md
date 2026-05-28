# Playwright Java + Codex Agentic Testing Guide

Practical reference for a Java + Maven + Playwright workflow where a recorded browser flow becomes a maintainable automation suite through Codex or another agentic assistant.

This guide is intentionally workflow-oriented. For this repository, the existing ATF architecture remains the source of truth. Use this guide to support recording, refactoring, self-healing, and agent-assisted execution without bypassing the framework structure described in [README.md](../README.md) and [Living_Architecture_UI_API_doc_v1_0.html](Living_Architecture_UI_API_doc_v1_0.html).

## Quick Flow

```text
record -> generate Playwright Java code -> AI refactor -> AI run -> AI self-heal -> rerun until green
```

## Table of Contents

- [0. Workflow Goal](#0-workflow-goal)
- [1. Required Tooling](#1-required-tooling)
- [2. Create A Java Maven Project](#2-create-a-java-maven-project)
- [3. Minimal pom.xml](#3-minimal-pomxml)
- [4. Install Playwright Browsers](#4-install-playwright-browsers)
- [5. Create An Initial Smoke Test](#5-create-an-initial-smoke-test)
- [6. Initial Recording With Playwright Codegen](#6-initial-recording-with-playwright-codegen)
- [7. What To Do With The Raw Recording](#7-what-to-do-with-the-raw-recording)
- [8. Full Prompt For Framework Generation And Positive/Negative Tests](#8-full-prompt-for-framework-generation-and-positivenegative-tests)
- [9. Short Prompt For Run And Auto-Fix](#9-short-prompt-for-run-and-auto-fix)
- [10. Prompt For Respecting An Existing Framework Structure](#10-prompt-for-respecting-an-existing-framework-structure)
- [11. How To Run The Tests](#11-how-to-run-the-tests)
- [12. If The Browser Window Is Not Visible](#12-if-the-browser-window-is-not-visible)
- [13. Optional: Playwright MCP](#13-optional-playwright-mcp)
- [14. Common Problems And Fixes](#14-common-problems-and-fixes)
- [15. Operational Summary](#15-operational-summary)

## 0. Workflow Goal

<details open>
<summary>Read section</summary>

The intended workflow is:

```text
record -> generate Playwright Java code -> AI refactor -> AI run -> AI self-heal -> rerun until green
```

In practice, you use Playwright Codegen to capture the first real browser flow. Then Codex or another AI agent refactors that flow into a Java + JUnit5 + Page Object Model automation structure and keeps running the tests until they become stable.

</details>

## 1. Required Tooling

<details open>
<summary>Read section</summary>

- Java 17+ or Java 21
- Maven
- Node.js + `npm` / `npx`
- IntelliJ IDEA
- ChatGPT / Codex with access to the project

### Quick checks

```bash
java -version
mvn -version
node -v
npm.cmd -v
npx.cmd --version
```

If PowerShell blocks `npm`, use the explicit Windows executables:

```bash
npm.cmd -v
npx.cmd --version
```

</details>

## 2. Create A Java Maven Project

<details open>
<summary>Read section</summary>

In IntelliJ:

```text
New Project -> Maven -> JDK 17+ / 21 -> Create
```

Or from the terminal:

```bash
mvn archetype:generate "-DgroupId=org.example" "-DartifactId=mcpTest" "-DarchetypeArtifactId=maven-archetype-quickstart" "-DinteractiveMode=false"
```

Open the project folder itself in IntelliJ, not the parent directory.

</details>

## 3. Minimal pom.xml

<details open>
<summary>Read section</summary>

Important: for `mvn exec:java` to run the Playwright CLI, the Playwright dependency must not exist only in `test` scope.

```xml
<dependencies>
    <dependency>
        <groupId>com.microsoft.playwright</groupId>
        <artifactId>playwright</artifactId>
        <version>1.53.0</version>
    </dependency>

    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.2.5</version>
        </plugin>
    </plugins>
</build>
```

</details>

## 4. Install Playwright Browsers

<details>
<summary>Read section</summary>

### Browsers for Playwright Java

```bash
mvn exec:java "-Dexec.mainClass=com.microsoft.playwright.CLI" "-Dexec.args=install"
```

### Browsers for Node Playwright / Codegen

```bash
npx.cmd playwright install
```

If you see an error such as `Executable doesn't exist ... ms-playwright ... chrome.exe`, run again:

```bash
npx.cmd playwright install
```

</details>

## 5. Create An Initial Smoke Test

<details>
<summary>Read section</summary>

File:

```text
src/test/java/org/example/SmokeTest.java
```

```java
package org.example;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;

public class SmokeTest {

    @Test
    void shouldOpenSite() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
            );

            Page page = browser.newPage();
            page.navigate("https://playwright.dev");
            System.out.println(page.title());

            browser.close();
        }
    }
}
```

Run it from IntelliJ by right-clicking the test, or from the terminal:

```bash
mvn test
```

</details>

## 6. Initial Recording With Playwright Codegen

<details>
<summary>Read section</summary>

Example for the registration page:

```bash
npx.cmd playwright codegen --target java https://practicesoftwaretesting.com/auth/register
```

This opens the browser and Playwright Inspector. Complete the flow manually: registration, validations, login, profile updates, password change, and so on.

At the end, copy the generated code and save it in a raw class such as:

```text
src/test/java/org/example/recordings/RegisterPrimFlow.java
```

That class is only a behavioral source, not final test code.

</details>

## 7. What To Do With The Raw Recording

<details>
<summary>Read section</summary>

Do not delete it. Keep it as a behavioral reference.

Example instruction for Codex:

```text
Use RegisterPrimFlow.java only as behavioral reference and flow discovery source.

Do NOT keep the raw recorder implementation.
Do NOT keep recorder-style click spam.
```

The raw recording has value because it shows real application behavior, available selectors, and observed validations.

</details>

## 8. Full Prompt For Framework Generation And Positive/Negative Tests

<details>
<summary>Read section</summary>

```text
Continue from the current framework and existing changes.

Use RegisterPrimFlow.java only as behavioral reference and flow discovery source.

Do NOT keep the raw recorder implementation.
Do NOT keep recorder-style click spam.

Goal:
Transform RegisterPrimFlow.java into a production-grade, self-healing Playwright Java automation suite with broad positive and negative functional coverage.

Architecture Rules:
- Reuse the current framework as the source of truth.
- Do not create a parallel architecture.
- Reuse existing:
  - BaseTest
  - Playwright setup
  - waits
  - utilities
  - assertion style
  - naming conventions
  - package structure
  - helper classes

Framework Requirements:
Create and/or reuse:
- BaseTest
- RegisterPage
- LoginPage
- AccountPage
- RegistrationData
- TestDataFactory
- reusable assertions
- reusable helper methods
- reusable locator methods

Testing Goals:
Generate as many meaningful positive and negative tests as possible from the recorded behavior and actual UI behavior.

Positive Scenarios:
- successful registration
- login after registration
- profile validation after login
- password change
- login with changed password
- persistence validation
- navigation validation
- account page validation

Negative Scenarios:
- invalid password validation
- password policy validation
- duplicate email validation
- invalid email validation
- invalid phone validation
- invalid postal code validation
- required field validation
- invalid country/state combinations if applicable
- empty field validation
- invalid password confirmation
- invalid login after password change
- invalid current password
- boundary conditions
- validation message assertions
- invalid form state validation

Technical Rules:
1. Use Page Object Model strictly.
2. Keep selectors centralized in Page Objects only.
3. Prefer locator strategy order:
   - data-test
   - label
   - role
   - placeholder
   - text only as last resort

4. Generate random unique emails automatically.
5. Avoid flaky waits and timing issues.
6. Prefer Playwright assertions over manual checks.
7. Tests must be independent and rerunnable.
8. Use browser visible mode by default.
9. Add reusable helper methods and data builders.
10. Remove duplicate locators and repeated logic.

Self-Healing / Autonomous Behavior:
When tests fail:
- inspect the exact error
- inspect the live DOM if selectors fail
- inspect real UI behavior if assertions fail
- repair selectors automatically
- repair waits automatically
- repair assertions automatically
- rerun failing tests
- continue iterating autonomously until green or until a real external blocker exists

Execution Rules:
- Run mvn test continuously during implementation.
- Fix compile/runtime/test failures automatically.
- Do not stop after first failure.
- Continue autonomous iteration.
- Re-run unstable tests multiple times to verify stability.

Approval Rules:
- Reuse already granted approvals.
- Prefer "don't ask again" for:
  - mvn
  - playwright
  - DOM inspection
  - safe read operations
  - repeated browser automation actions

Code Quality Rules:
- no recorder-style click spam
- no magic sleeps unless absolutely necessary
- no duplicated locators
- no duplicated assertions
- readable method names
- reusable utilities
- production-grade Playwright Java practices
- clean maintainable code

Final Validation:
Before finishing:
1. Run the full Maven suite.
2. Ensure all tests pass.
3. Re-run the suite multiple times for stability verification.
4. Verify selectors are stable.
5. Verify tests are isolated and rerunnable.
6. Summarize:
   - tests created
   - files created/modified
   - failures fixed
   - selector repairs performed
   - final mvn test result
   - remaining flaky/risky areas if any
```

</details>

## 9. Short Prompt For Run And Auto-Fix

<details>
<summary>Read section</summary>

```text
Run mvn test.

If tests fail:
- inspect the exact failure
- identify whether it is compile, selector, timing, validation, network, or test-data issue
- inspect the live DOM if selectors or assertions fail
- fix the code
- rerun mvn test
- continue until all tests pass or until a real external blocker exists.

Do not stop after the first failure.
```

</details>

## 10. Prompt For Respecting An Existing Framework Structure

<details>
<summary>Read section</summary>

If you already have an existing framework or template inside the project, give Codex something like:

```text
Treat the existing framework structure as the source of truth.

Do not invent a new architecture.

Before generating new files:
- inspect the current package structure
- inspect existing base classes
- inspect utilities
- inspect page object conventions
- inspect assertion style
- inspect waits strategy

Extend the existing framework consistently.

Reuse existing:
- package layout
- naming conventions
- base classes
- utility classes
- Playwright setup
- assertion style
- helper methods

Avoid duplicate utilities or parallel architectures.
```

</details>

## 11. How To Run The Tests

<details>
<summary>Read section</summary>

### From IntelliJ

- Right-click the test class -> Run
- Right-click -> Debug for breakpoints

### From the terminal

```bash
mvn test
```

### A single test

```bash
mvn -Dtest=RegisterUserTest test
```

</details>

## 12. If The Browser Window Is Not Visible

<details>
<summary>Read section</summary>

Find where the browser is created, usually in `BaseTest` or `PlaywrightTestBase`:

```java
new BrowserType.LaunchOptions().setHeadless(true)
```

Change it to:

```java
new BrowserType.LaunchOptions().setHeadless(false)
```

Or make it configurable:

```java
boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

browser = playwright.chromium().launch(
    new BrowserType.LaunchOptions().setHeadless(headless)
);
```

Headless run:

```bash
mvn test -Dheadless=true
```

</details>

## 13. Optional: Playwright MCP

<details>
<summary>Read section</summary>

For the workflow above, explicit MCP is not mandatory. In practice, you are already using:

```text
Codex + Playwright CLI + Codegen + Maven execution
```

If you want Playwright MCP installed:

```bash
npm.cmd install -g @playwright/mcp
```

Manual start:

```bash
npx.cmd @playwright/mcp@latest
```

Important: in IntelliJ or Codex, the main value usually comes from:

```text
run -> fail -> inspect -> fix -> rerun
```

not necessarily from manually running an MCP server.

</details>

## 14. Common Problems And Fixes

<details>
<summary>Read section</summary>

### `npm` blocked by PowerShell

```text
npm : cannot be loaded because running scripts is disabled
```

Use:

```bash
npm.cmd -v
npm.cmd install -g @playwright/mcp
npx.cmd playwright install
```

### `ClassNotFoundException: com.microsoft.playwright.CLI`

This happens if the Playwright dependency is only in `test` scope and you run `exec:java`. Remove `<scope>test</scope>` from the Playwright dependency.

### `Executable doesn't exist ... ms-playwright`

Run:

```bash
npx.cmd playwright install
```

### The agent asks for approval on every command

Choose an option like:

```text
Yes, and don't ask again for commands that start with mvn test
```

</details>

## 15. Operational Summary

<details>
<summary>Read section</summary>

```text
1. Create a Java Maven project.
2. Add Playwright + JUnit5 + Surefire.
3. Install browsers for Java Playwright.
4. Install browsers for Node Playwright / Codegen.
5. Create SmokeTest and verify that it runs.
6. Run codegen on the desired page.
7. Save the raw recording as RegisterPrimFlow.java.
8. Send the full prompt to Codex.
9. Codex creates the POM plus positive/negative tests.
10. Codex runs mvn test.
11. Codex repairs selectors, assertions, and waits.
12. Repeat until the tests are green.
```

</details>
