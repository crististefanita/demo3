# run_02 ATF/support delta

New layered support introduced in this run:

- `src/main/java/com/endava/ai/ui/pages/ProfilePage.java`
- `src/main/java/com/endava/ai/ui/service/ProfileService.java`
- `src/test/java/com/endava/ai/atf/ui/component/AuthenticatedContinuationContractTest.java`

Cold reading:

- profile entry is now a first-class ATF seam
- the authenticated branch no longer stops at account shell only
- later password and profile-value checks can build on a named profile owner
