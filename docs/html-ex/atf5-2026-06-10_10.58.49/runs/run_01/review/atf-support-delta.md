# run_01 ATF/support delta

New layered support introduced in this run:

- `src/main/java/com/endava/ai/ui/pages/AccountPage.java`
- `src/main/java/com/endava/ai/ui/service/LoginService.java`
- `src/main/java/com/endava/ai/ui/validation/LoginValidation.java`
- `src/test/java/com/endava/ai/atf/ui/component/AuthenticatedContinuationContractTest.java`
- `testng.xml`

Cold reading:

- the repo now has an ATF-owned login/account seam
- account-surface truth is no longer implicit in the seed only
- further profile/password descendants can now build on a named owned base
