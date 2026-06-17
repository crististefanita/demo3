# Run manifest

- run: run_03
- class: src/test/java/com/endava/ai/atf/ui/commerce/GuestInvoiceReceiptOwnershipProbeTests.java
- command: mvn -q -Dsurefire.suiteXmlFiles=target/run_03-suite.xml test
- extent: validation/reports/ExtentReport_2026-06-17_14-57-26.html
- machine proof: validation/GuestInvoiceReceiptOwnershipProbeTests.xml
