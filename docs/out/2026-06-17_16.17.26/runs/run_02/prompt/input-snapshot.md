# input-snapshot

- class: com.endava.ai.ui.tests.AuthenticatedOrdersPromiseContradictionTests
- winner family: payment success lacks a persistent commercial identity and still cannot bridge into receipt id, order row or invoice row
- local claim: payment success coexists with an account promise about orders while invoice history stays empty
- expected delta: the contradiction is raised from empty downstream pages to a product promise mismatch: the account overview promises orders, but no invoice row materializes even after successful payment
