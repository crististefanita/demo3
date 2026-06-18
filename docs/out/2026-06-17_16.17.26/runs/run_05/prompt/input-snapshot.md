# input-snapshot

- class: com.endava.ai.ui.tests.AuthenticatedReceiptIdentityAfterReloginTests
- winner family: payment success lacks a persistent commercial identity and still cannot bridge into receipt id, order row or invoice row
- local claim: relogin still cannot recover a persistent commercial identity after successful purchase
- expected delta: the fallback remains negative after a cold reopen: the authenticated customer still sees no persistent receipt, order row or invoice row after relogin
