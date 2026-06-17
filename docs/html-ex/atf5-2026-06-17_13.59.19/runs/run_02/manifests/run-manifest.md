# Run manifest

- run: run_02
- class: src/test/java/com/endava/ai/atf/ui/commerce/GuestIdentityToBillingOwnershipTests.java
- command: mvn -q "-Dsurefire.suiteXmlFiles=target/run-02-suite.xml" test
- extent: validation/reports/ExtentReport_2026-06-17_13-55-37.html
- machine proof: validation/GuestIdentityToBillingOwnershipTests.xml