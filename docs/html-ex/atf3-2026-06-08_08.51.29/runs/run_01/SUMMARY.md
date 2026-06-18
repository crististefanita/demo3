# run_01 Summary

- `outcome = 1 green / 2 red`
- `green = AccountRouteContinuityTests`
- `red = AccountPasswordMutationPositiveContinuityTests, AccountPasswordMutationTests`
- `hypothesis = longer settle after password-change submit may expose a deeper product blocker than the prior same-family split`
- `what moved = both mutation paths now fail at the same colder checkpoint`
  - `URL = /account/profile`
  - `protected profile surface = not defended`
- `why retained = this is the first run that replaces the old split with a same-family cap`
