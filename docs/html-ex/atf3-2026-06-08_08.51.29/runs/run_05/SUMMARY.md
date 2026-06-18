# run_05 Summary

- `outcome = 0 green / 1 red`
- `red = both_old_and_new_credentials_can_reopen_the_same_protected_identity_after_the_claimed_change`
- `hypothesis = the same identity may allow both credentials to coexist after the claimed change`
- `what moved = the same identity reopened the protected profile with the old credential, then rejected the new credential on the next fresh login`
- `why retained = this is the strongest same-identity credential-semantics cap of the package`
- `why winner = it ties old-retained truth directly to new-rejected truth after a claimed password change`
