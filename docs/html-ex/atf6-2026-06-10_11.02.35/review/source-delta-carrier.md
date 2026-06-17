# Source Delta Carrier

## Ownership

- source carrier scope = `root-level`
- authoritative source verdict owner = `root-level + review/source-delta-carrier.md`
- extra artifact carries published claim = `yes`
- named HTML owner = `5.5.2a #s552a`
- why root-level = package truth depends on one package-wide source verdict, not on separate run-local verdict files
- run-local support bundle = `runs/run_01/atf/`

## Actual Source/Test Delta In This Package

- new source file = `src/main/java/com/endava/ai/ui/pages/ProfilePage.java`
- new source file = `src/main/java/com/endava/ai/ui/service/ProfileService.java`
- new source file = `src/main/java/com/endava/ai/ui/validation/ProfileValidation.java`
- new test file = `src/test/java/com/endava/ai/atf/ui/AuthenticatedProfileTests.java`

## Why It Exists

- the previous package already owned account overview, but not a breadth slice beyond it
- the profile slice needed reusable ATF support to prove persisted registration data across the authenticated form
- the support is intentionally narrow: it owns one sibling breadth slice without pretending to own the rest of authenticated breadth

## Saved Run-Local Support Bundle

- copied source/test delta bundle = `runs/run_01/atf/`
