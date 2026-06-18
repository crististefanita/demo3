# input-snapshot

- class: com.endava.ai.ui.tests.AuthenticatedCommercialEntryTests
- winner family: payment success lacks a persistent commercial identity and still cannot bridge into receipt id, order row or invoice row
- local claim: authenticated customer can reach account, cart and payment surface before any post-purchase object exists
- expected delta: baseline commercial entry is real: account overview, invoices shell, cart materialization and payment surface are all reachable for an authenticated customer
