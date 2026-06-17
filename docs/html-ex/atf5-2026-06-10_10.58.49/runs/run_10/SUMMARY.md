# run_10

Role: `deeper-branch`

This run spends the final governed slot on a materially expanded registration redirect-failure boundary.
The repo already had a cold registration validator seam, but it did not yet surface failure context around the redirect-to-login expectation with explicit visible register-error evidence.

Decisive proof:

- `validation/command-output.txt`
- `validation/command-result.md`
- `validation/testng-results.xml`
- `atf/RegistrationValidation.java`
- `atf/RegistrationValidationContractTest.java`

Outcome:

- registration redirect failure is now explicit and contract-backed
- the round owns one more local boundary without pretending live registration success was restored
- the governed window ends on a final real material delta, not on decorative close-out
