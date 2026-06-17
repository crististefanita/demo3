# run_04

Role: `deeper-branch`

This run adds data-truth ownership on the profile surface.
The authenticated continuation slice now checks that saved profile values materialize correctly, not only that the page opens or a password action can be submitted.

Decisive proof:

- `validation/command-output.txt`
- `validation/command-result.md`
- `validation/testng-results.xml`
- `atf/ProfileValidation.java`
- `atf/AuthenticatedContinuationContractTest.java`

Outcome:

- profile identity fields are now contract-backed
- the branch owns value materialization on profile
- authenticated continuation now includes data-truth, not just navigation and mutation
