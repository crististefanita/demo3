# run_09

Role: `deeper-branch`

This run materializes the negative login branch explicitly in contracts.
The authenticated slice no longer owns only happy-path entry; it now also owns staying on login and surfacing the expected login error.

Decisive proof:

- `validation/command-output.txt`
- `validation/command-result.md`
- `validation/testng-results.xml`
- `atf/AuthenticatedContinuationContractTest.java`

Outcome:

- login negative branch is now explicit and contract-backed
- the authenticated slice owns both positive account-entry expectation and negative login boundary
- this closes the authenticated negative entry boundary and hands the tenth slot to a fresh rerank instead of to automatic closure
