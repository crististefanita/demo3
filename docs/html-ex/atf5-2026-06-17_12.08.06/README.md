# ROOT_RUN 2026-06-17_12.08.06

Winner frontier:

`authenticated customer -> product favorite -> favorites collection persistence`

Fresh-root result:

- `runs consumed = 5 / 5`
- `tests produced = 25`
- every consumed run closed at `5 / 5` passed

Business verdict:

This root proves a real persistent favorite row for an authenticated customer. The row is created from product detail, reopened from the favorites surface, survives sign out / sign in, stays separated from guest attempts and other customer sessions, and is not sold as fake breadth when the app reopens the same product through related-product navigation.

Score posture:

- persistent favorite row permits `Overall > 90`
- multi-product favorites are still partial because the current related-product path reopens the same product identity
- invoice row, message row and order row remain unproved

Repair history:

- `run_03`: one repair, sign-out stabilized from favorites surface
- `run_04`: one repair, sign-out stabilized from favorites surface before account switching
- `run_05`: one repair, false second-product breadth downgraded to honest same-product dedupe verdict
