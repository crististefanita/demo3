# Run manifest

- run: run_03
- class: src/test/java/com/endava/ai/atf/ui/commerce/GuestBillingToPaymentOwnershipTests.java
- command: mvn -q "-Dsurefire.suiteXmlFiles=target/run-03-suite.xml" test
- extent: validation/reports/ExtentReport_2026-06-17_13-56-31.html
- machine proof: validation/GuestBillingToPaymentOwnershipTests.xml