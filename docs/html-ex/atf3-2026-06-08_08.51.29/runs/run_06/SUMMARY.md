# run_06 Summary

- `outcome = 1 green / 0 red`
- `green = changed_password_can_reopen_the_same_favorites_state_with_the_new_credential`
- `hypothesis = a second protected zone may still stay green after the claimed password change when the new credential reopens it cold`
- `what moved = after seeding the same favorite before mutation, the new credential reopened the same favorites state after session clear`
- `why retained = this proves the password family still has a real green companion on a second protected business zone, not only on profile`
- `why it matters = the branch no longer reads like profile-only evidence; favorites also stays recoverable on the new credential path`
