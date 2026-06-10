# run_05

Role: `deeper-branch`

This run closes the open-profile chain into one owned path:

- open account
- wait for account surface
- click profile
- wait for profile surface
- confirm the profile actually loads data

Decisive proof:

- `validation/command-output.txt`
- `validation/command-result.md`
- `validation/testng-results.xml`
- `atf/ProfileValidation.java`
- `atf/AuthenticatedContinuationContractTest.java`

Outcome:

- the branch owns a complete local account-to-profile opening path
- profile loaded-state is explicit
- later sibling expansion can now rely on a colder authenticated base chain
