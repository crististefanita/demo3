# Run manifest

- run: run_04
- class: src/test/java/com/endava/ai/atf/ui/commerce/GuestOrderHistoryOwnershipProbeTests.java
- command: mvn -q -Dsurefire.suiteXmlFiles=target/run_04-suite.xml test
- extent: validation/reports/ExtentReport_2026-06-17_14-58-10.html
- machine proof: validation/GuestOrderHistoryOwnershipProbeTests.xml
