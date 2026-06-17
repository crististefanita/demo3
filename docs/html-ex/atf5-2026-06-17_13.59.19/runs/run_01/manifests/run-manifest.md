# Run manifest

- run: run_01
- class: src/test/java/com/endava/ai/atf/ui/commerce/GuestCheckoutBoundaryTests.java
- command: mvn -q "-Dsurefire.suiteXmlFiles=target/run-01-suite.xml" test
- extent: validation/reports/ExtentReport_2026-06-17_13-54-34.html
- machine proof: validation/GuestCheckoutBoundaryTests.xml