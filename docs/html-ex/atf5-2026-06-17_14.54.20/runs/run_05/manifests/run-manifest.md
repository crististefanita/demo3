# Run manifest

- run: run_05
- class: src/test/java/com/endava/ai/atf/ui/commerce/GuestCommercialClosureOwnershipProbeTests.java
- command: mvn -q -Dsurefire.suiteXmlFiles=target/run_05-suite.xml test
- extent: validation/reports/ExtentReport_2026-06-17_14-58-52.html
- machine proof: validation/GuestCommercialClosureOwnershipProbeTests.xml
