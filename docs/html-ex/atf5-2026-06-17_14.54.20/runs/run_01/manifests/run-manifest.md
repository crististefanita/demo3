# Run manifest

- run: run_01
- class: src/test/java/com/endava/ai/atf/ui/commerce/GuestPostPaymentOwnershipBoundaryTests.java
- command: mvn -q -Dsurefire.suiteXmlFiles=target/run_01-suite.xml test
- extent: validation/reports/ExtentReport_2026-06-17_14-55-14.html
- machine proof: validation/GuestPostPaymentOwnershipBoundaryTests.xml
