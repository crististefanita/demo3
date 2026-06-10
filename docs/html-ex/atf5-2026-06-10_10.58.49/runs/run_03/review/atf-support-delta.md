# run_03 ATF/support delta

New layered support introduced in this run:

- `src/main/java/com/endava/ai/ui/service/ProfileService.java`
- `src/main/java/com/endava/ai/ui/validation/ProfileValidation.java`
- `src/test/java/com/endava/ai/atf/ui/component/AuthenticatedContinuationContractTest.java`

Cold reading:

- password mutation is now represented explicitly in ATF layers
- success feedback is contract-backed
- the profile branch gained its first owned mutating action
