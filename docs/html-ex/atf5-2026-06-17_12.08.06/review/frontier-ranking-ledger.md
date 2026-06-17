# Frontier ranking ledger

## Ranked candidates

1. `authenticated customer -> product favorite -> favorites collection persistence`
2. `authenticated customer -> invoices row persistence`
3. `authenticated customer -> support message persistence`

## Ranking verdict

Winner: `authenticated customer -> product favorite -> favorites collection persistence`

Why it wins:

- directly continues the closed local benchmark frontier;
- upgrades the customer container from empty shell to persistent owned row;
- has visible selectors and route anchors in the live codebase;
- can produce a stronger business object than empty favorites / invoices / messages shells.

Why the others are not selected first:

- invoice row persistence has no live purchase proof chain in the current customer package;
- message persistence depends on support-case lifecycle not yet reopened from account messages in this root.
