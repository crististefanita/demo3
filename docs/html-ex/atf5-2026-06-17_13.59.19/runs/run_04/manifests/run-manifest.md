# Run manifest

- run: run_04
- class: src/test/java/com/endava/ai/atf/ui/commerce/GuestPaymentConfirmationOwnershipTests.java
- command: mvn -q "-Dsurefire.suiteXmlFiles=target/run-04-suite.xml" test
- extent: validation/reports/ExtentReport_2026-06-17_13-57-18.html
- machine proof: validation/GuestPaymentConfirmationOwnershipTests.xml