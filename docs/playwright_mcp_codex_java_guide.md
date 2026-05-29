# Playwright MCP + Codex + Java Guide

Practical step-by-step guide for using `Playwright + MCP + Codex` in a Java + Maven workflow.

For this repository, the existing ATF remains the source of truth. Use Playwright for browser capture and browser interaction, use Codex to refactor and stabilize tests, and use the framework layers plus Maven validation to decide whether the generated result is good enough or whether the ATF itself must be improved.

## Quick Flow

```text
configure MCP -> verify tools -> record browser flow -> generate/refactor in existing ATF -> run -> inspect -> harden -> rerun
```

## Table of Contents

- [0. Workflow Goal](#0-workflow-goal)
- [1. Required Tooling](#1-required-tooling)
- [2. Configure Playwright MCP In Codex](#2-configure-playwright-mcp-in-codex)
- [3. Verify The MCP Setup](#3-verify-the-mcp-setup)
- [4. Create Or Open The Java Maven Project](#4-create-or-open-the-java-maven-project)
- [5. Minimal pom.xml](#5-minimal-pomxml)
- [6. Install Playwright Browsers](#6-install-playwright-browsers)
- [7. Create An Initial Smoke Test](#7-create-an-initial-smoke-test)
- [8. Record The First Real Flow With Playwright Codegen](#8-record-the-first-real-flow-with-playwright-codegen)
- [9. What To Do With The Raw Recording](#9-what-to-do-with-the-raw-recording)
- [10. Prompts For Codex](#10-prompts-for-codex)
- [11. How To Run The Tests](#11-how-to-run-the-tests)
- [12. Common Problems And Fixes](#12-common-problems-and-fixes)
- [13. Operational Summary](#13-operational-summary)

## 0. Workflow Goal

<details>
<summary>Read section</summary>

The intended workflow is:

```text
configure MCP -> verify tools -> record browser flow -> AI refactor -> AI run -> AI self-heal -> rerun until green
```

In practice:

- Playwright Codegen captures the first real browser flow
- Codex converts that flow into framework-compliant Java tests
- Maven and report artifacts verify whether the generated tests are stable
- if the generated tests expose framework weaknesses, improve the ATF with better support classes, validators, waits, reporting, or contract tests

For this repository, generated tests are only part of the outcome. The more valuable outcome is a stronger reusable ATF core.

</details>

## 1. Required Tooling

<details>
<summary>Read section</summary>

- Java 17+ or Java 21
- Maven
- Node.js with `npm` / `npx`
- IntelliJ IDEA
- Codex with MCP support
- Playwright MCP configured in Codex

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

## 2. Configure Playwright MCP In Codex

<details>
<summary>Read section</summary>

This step is required. Without it, Codex will not have Playwright MCP tools available in the session.

Add the Playwright MCP server to your Codex MCP configuration file or MCP settings area:

```json
{
  "mcpServers": {
    "playwright": {
      "command": "npx.cmd",
      "args": ["@playwright/mcp@latest"]
    }
  }
}
```

Important notes:

- if you already have other MCP servers configured, add only the `playwright` entry under `mcpServers`; do not delete the existing ones
- on Windows, prefer `npx.cmd`, not `npx`
- `@playwright/mcp@latest` lets Codex launch the MCP server on demand
- save the configuration, then restart or reload Codex so the new MCP server is picked up

This is the minimum MCP configuration needed for Playwright browser automation from Codex.

</details>

## 3. Verify The MCP Setup

<details>
<summary>Read section</summary>

Before recording or generating tests, confirm the environment is ready:

1. the MCP configuration was saved
2. Codex was restarted or a fresh session was opened
3. Node and `npx.cmd` work locally
4. Playwright browsers can be installed

Practical verification commands:

```bash
npx.cmd @playwright/mcp@latest --help
npx.cmd playwright install
```

Expected outcome:

- the first command should start or describe the MCP package without a `command not found` error
- the second command should install the Node Playwright browsers required for Codegen and MCP-driven browsing

If Codex still does not expose Playwright MCP tools after this, recheck the JSON configuration and reopen the session.

If needed, test in a fresh Codex thread after restart so the tool list is reloaded cleanly.

</details>

## 4. Create Or Open The Java Maven Project

<details>
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

If you are working in this repository, open the repository root and reuse the existing framework structure instead of creating a parallel project.

</details>

## 5. Minimal pom.xml

<details>
<summary>Read section</summary>

Important: if you want to run the Playwright Java CLI with `mvn exec:java`, the Playwright dependency must not exist only in `test` scope.

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

For this repository specifically, prefer the existing `pom.xml` and existing test stack rather than replacing it with a standalone JUnit sample.

</details>

## 6. Install Playwright Browsers

<details>
<summary>Read section</summary>

You normally need both browser installation paths:

### Browsers for Playwright Java

```bash
mvn exec:java "-Dexec.mainClass=com.microsoft.playwright.CLI" "-Dexec.args=install"
```

### Browsers for Node Playwright / Codegen / MCP

```bash
npx.cmd playwright install
```

If you see an error such as `Executable doesn't exist ... ms-playwright ... chrome.exe`, run again:

```bash
npx.cmd playwright install
```

</details>

## 7. Create An Initial Smoke Test

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

Run it from IntelliJ or from the terminal:

```bash
mvn test
```

The goal here is simple: prove that Playwright Java works before asking Codex to generate more framework code.

</details>

## 8. Record The First Real Flow With Playwright Codegen

<details>
<summary>Read section</summary>

Example for the registration page:

```bash
npx.cmd playwright codegen --target java https://practicesoftwaretesting.com/auth/register
```

This opens the browser and Playwright Inspector. Complete the flow manually: registration, validations, login, profile updates, password change, and so on.

At the end, copy the generated code into a raw class such as:

```text
src/test/java/org/example/recordings/RegisterPrimFlow.java
```

That raw class is only a behavioral reference. It is not final framework code.

</details>

## 9. What To Do With The Raw Recording

<details>
<summary>Read section</summary>

Keep the raw recording as a behavioral source.

Do not treat it as production test code.

Example instruction for Codex:

```text
Use RegisterPrimFlow.java only as behavioral reference and flow discovery source.

Do NOT keep the raw recorder implementation.
Do NOT keep recorder-style click spam.
Reuse the existing ATF layers and refactor the flow into framework-compliant tests.
```

The raw recording is useful because it preserves:

- real application behavior
- observed selectors
- navigation order
- validation messages
- data dependencies discovered during the recording

</details>

## 10. Prompts For Codex

<details>
<summary>Read section</summary>

For this repository, start from the existing prompts:

- API-focused work: [api-framework-evolution.md](prompts/api-framework-evolution.md)
- UI recording conversion: [ui-recording-to-atf-tests.md](prompts/ui-recording-to-atf-tests.md)
- ATF-level hardening: [atf-framework-test-evolution.md](prompts/atf-framework-test-evolution.md)

### Minimal prompt pattern

```text
Continue from the existing ATF.

Treat the current repository structure, lifecycle, reporting, listeners, waits, validators, and utilities as the source of truth.

Use the recorded Playwright flow only as behavioral reference.

Generate the smallest safe framework-compliant change.
Run the narrowest relevant Maven tests.
If the generated test reveals a framework gap, improve the ATF with reusable support classes, validators, adapters, or component/contract tests.
Keep iterating until the change is stable.
```

### Short run-and-fix prompt

```text
Run the relevant Maven tests.

If tests fail:
- inspect the exact failure
- inspect the live DOM if selectors fail
- inspect reports and artifacts
- fix the smallest real root cause
- rerun the affected tests
- continue until green or until a real external blocker exists
```

The important part is not prompt length. It is making Codex preserve the existing framework and work iteratively.

</details>

## 11. How To Run The Tests

<details>
<summary>Read section</summary>

### From IntelliJ

- right-click the test class and run
- use debug when you need breakpoints

### From the terminal

```bash
mvn test
```

### Single test class

```bash
mvn -Dtest=RegisterUserTest test
```

### Recommended progression

1. run the narrowest impacted class
2. inspect the failure, report, or browser behavior
3. let Codex fix the smallest root cause
4. rerun the affected area
5. broaden validation only after the narrow loop is green

</details>

## 12. Common Problems And Fixes

<details>
<summary>Read section</summary>

### `npm` blocked by PowerShell

```text
npm : cannot be loaded because running scripts is disabled
```

Use:

```bash
npm.cmd -v
npx.cmd --version
npx.cmd playwright install
```

### Codex does not see Playwright MCP tools

Check all of the following:

- the MCP config JSON contains the `playwright` server entry
- the command is `npx.cmd`
- the args are `["@playwright/mcp@latest"]`
- the config file was saved
- Codex was restarted or a fresh session was opened

### `ClassNotFoundException: com.microsoft.playwright.CLI`

This usually means the Playwright dependency is only in `test` scope and you are trying to run `exec:java`. Remove `<scope>test</scope>` from the Playwright dependency if you need the Java CLI path.

### `Executable doesn't exist ... ms-playwright`

Run:

```bash
npx.cmd playwright install
```

### Browser opens headless and you want it visible

Check the browser launch options and use:

```java
new BrowserType.LaunchOptions().setHeadless(false)
```

For this repository, keep browser behavior aligned with the existing ATF configuration instead of forcing ad hoc launch logic in tests.

</details>

## 13. Operational Summary

<details>
<summary>Read section</summary>

```text
1. Install Java, Maven, Node, and IntelliJ.
2. Add the Playwright MCP server to the Codex MCP config.
3. Restart Codex and verify the MCP setup.
4. Install Playwright browsers for Java and Node.
5. Open the existing project or create a Maven project.
6. Verify a minimal SmokeTest.
7. Record the first real browser flow with Playwright Codegen.
8. Save the raw recording as behavioral reference only.
9. Ask Codex to refactor inside the existing ATF.
10. Run narrow Maven validation.
11. If generated tests expose framework gaps, harden the ATF.
12. Rerun until the tests and framework behavior are stable.
```

</details>
