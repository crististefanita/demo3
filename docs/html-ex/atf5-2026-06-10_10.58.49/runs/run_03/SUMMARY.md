# run_03

Role: `deeper-branch`

This run turns password mutation on the profile surface into an owned ATF workflow with a success assertion.
The authenticated branch now owns both the submit sequence and the success-banner proof for password change.

Decisive proof:

- `validation/command-output.txt`
- `validation/command-result.md`
- `validation/testng-results.xml`
- `atf/ProfileService.java`
- `atf/ProfileValidation.java`
- `atf/AuthenticatedContinuationContractTest.java`

Outcome:

- password change is no longer seed-only memory
- the profile branch now has its first mutable business action
- the branch gained deeper business consequence, not only navigation depth
