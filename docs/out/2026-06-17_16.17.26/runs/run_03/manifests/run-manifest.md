# run-manifest

- run_id: run_03
- class: com.endava.ai.ui.tests.AuthenticatedReceiptIdentityOnSuccessTests
- claim: checkout success stays generic and exposes no order id or receipt identity
- intended delta: the blocker moves upstream to the first missing commercial identity on the success surface itself, before orders or invoices are even reopened
