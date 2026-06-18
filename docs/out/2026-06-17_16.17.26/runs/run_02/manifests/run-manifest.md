# run-manifest

- run_id: run_02
- class: com.endava.ai.ui.tests.AuthenticatedOrdersPromiseContradictionTests
- claim: payment success coexists with an account promise about orders while invoice history stays empty
- intended delta: the contradiction is raised from empty downstream pages to a product promise mismatch: the account overview promises orders, but no invoice row materializes even after successful payment
