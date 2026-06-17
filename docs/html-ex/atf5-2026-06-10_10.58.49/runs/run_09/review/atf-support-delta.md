# run_09 ATF/support delta

New layered support introduced in this run:

- `src/test/java/com/endava/ai/atf/ui/component/AuthenticatedContinuationContractTest.java`

Cold reading:

- login negative branch is now owned in the contract suite
- the authenticated slice closes with a clearer boundary around failed login
- the delta is test-only, but it changes what the round can claim
