# run_10 ATF/support delta

New layered support introduced in this run:

- `src/main/java/com/endava/ai/ui/validation/RegistrationValidation.java`
- `src/test/java/com/endava/ai/atf/ui/component/RegistrationValidationContractTest.java`

Cold reading:

- registration redirect-to-login now carries explicit failure-context evidence
- visible register-error context is no longer implicit
- the delta expands an already-cold seam instead of replaying it
