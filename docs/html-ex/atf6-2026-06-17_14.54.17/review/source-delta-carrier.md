# Source Delta Carrier

- package id: `2026-06-17_14.54.17`
- macro-lane: `post-purchase ownership materialization`
- compiled source owner: `review/root-source-thin-no-index.diff`
- new source introduced:
  - `src/test/java/com/endava/ai/ui/tests/checkout/authenticated/AbstractAuthenticatedCheckoutTest.java`
  - `src/test/java/com/endava/ai/ui/tests/checkout/postpurchase/AbstractPostPurchaseOwnershipTest.java`
  - `src/test/java/com/endava/ai/ui/tests/checkout/postpurchase/PostPurchaseInvoiceSurfaceTests.java`
  - `src/test/java/com/endava/ai/ui/tests/checkout/postpurchase/PostPurchaseInvoiceCustomerBoundaryTests.java`
  - `src/test/java/com/endava/ai/ui/tests/checkout/postpurchase/PostPurchaseInvoiceReloginTests.java`
  - `src/test/java/com/endava/ai/ui/tests/checkout/postpurchase/PostPurchaseReceiptIdentityGapTests.java`
  - `src/test/java/com/endava/ai/ui/tests/checkout/postpurchase/PostPurchaseOrderRowReloginTests.java`
- compatibility repairs carried during live execution:
  - checkout authentication helper now retries registration/login once before failing the slot
  - customer-boundary run now reanchors the final second-customer reopen through account overview before invoices
  - reopened invoices no longer misuse a helper that forbids legitimate ledger headers
