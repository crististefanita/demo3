# run_03 Summary

- `outcome = 1 green / 0 red`
- `green = old_credential_can_still_reopen_account_overview_after_the_claimed_password_change`
- `hypothesis = the branch may have moved beyond same-session profile-surface decay into post-logout old-credential retention`
- `what moved = after the claimed password change, the old credential still reopened account overview on a fresh-account path`
- `why retained = this is the first saved auth-surface proof that the claimed change does not reliably invalidate the old credential`
