# Run manifest

- run: run_02
- class: src/test/java/com/endava/ai/atf/ui/commerce/GuestOrderNumberOwnershipProbeTests.java
- command: mvn -q -Dsurefire.suiteXmlFiles=target/run_02-suite.xml test
- extent: validation/reports/ExtentReport_2026-06-17_14-56-41.html
- machine proof: validation/GuestOrderNumberOwnershipProbeTests.xml
