# input-snapshot

- class: com.endava.ai.ui.tests.AuthenticatedReceiptIdentityOnSuccessTests
- winner family: payment success lacks a persistent commercial identity and still cannot bridge into receipt id, order row or invoice row
- local claim: checkout success stays generic and exposes no order id or receipt identity
- expected delta: the blocker moves upstream to the first missing commercial identity on the success surface itself, before orders or invoices are even reopened
