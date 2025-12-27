# UI Test Automation Framework (Selenium + Playwright, ExtentReports)

## Requirements implemented
- Root package: `com.endava.ai.ui`
- Configuration via `src/main/resources/framework.properties` accessed ONLY through `ConfigManager`
- Pluggable UI engines (`selenium`, `playwright`, `default`)
- Pluggable reporting engines via adapters (Extent implemented)
- Centralized step-based logging using `StepLogger` + `ReportLogger`
- Screenshots captured ONLY by `TestListener` (exactly once per failing test)
- Stacktraces rendered ONLY once per failed step and ONLY as code blocks (Extent MarkupHelper)

## Setup
- Java 11+
- Maven 3.8+

## Run
```bash
mvn -q test
```

## Switch UI engine
Edit `framework.properties`:
- `ui.engine=selenium`
- `ui.engine=playwright`
- `ui.engine=default` (maps to selenium)

## Reports
Reports are written under `reports.dir`.
When `reports.timestamp.enabled=true`, file name is `ExtentReport_{timestamp}.html`.

## Notes (Demo site)
The demo site is JS-driven and can change. Locators are intentionally defensive CSS selectors, but still CSS-only.
