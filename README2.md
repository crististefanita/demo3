# API Test Automation Framework

## Setup
- Java 11
- Maven

## Configure
- `src/main/resources/framework.properties`
- Set `auth.token` (GoRest write operations)

## Run
- `mvn clean test`

## Reporting
- ExtentReports HTML is written under `reports.dir`
- Console + file step logging is produced per run
