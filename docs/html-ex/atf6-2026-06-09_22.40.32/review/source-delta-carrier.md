# Source Delta Carrier

## Actual Source/Test Delta In This Package

- new source file = `src/main/java/com/endava/ai/ui/pages/AccountPage.java`
- new source file = `src/main/java/com/endava/ai/ui/service/LoginService.java`
- new source file = `src/main/java/com/endava/ai/ui/service/AccountService.java`
- new source file = `src/main/java/com/endava/ai/ui/validation/AccountValidation.java`
- new test file = `src/test/java/com/endava/ai/atf/ui/AuthenticatedAccountTests.java`

## Why It Exists

- `run_02` proved that the parent happy path is live again
- `run_03` needed reusable ATF support for `login -> authenticated account overview`
- the support is intentionally narrow: it owns one authenticated continuation without pretending to own all descendant flows

## Saved Local Carrier

- copied source/test delta bundle = `runs/run_03/atf/`
