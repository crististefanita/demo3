# Run manifest

- run: run_05
- class: src/test/java/com/endava/ai/atf/ui/commerce/GuestOrderNumberOwnershipProbeTests.java
- command: mvn -q "-Dsurefire.suiteXmlFiles=target/run-05-suite.xml" test
- extent: validation/reports/ExtentReport_2026-06-17_13-58-08.html
- machine proof: validation/GuestOrderNumberOwnershipProbeTests.xml