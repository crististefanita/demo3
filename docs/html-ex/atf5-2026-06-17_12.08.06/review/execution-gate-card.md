# Execution gate card

Gate status: `closed`.

## Runtime counters

- `ROUND_TARGET_TEST_COUNT = 5`
- `META_ITERATION_COUNT = 5`
- `tests produced = 25`
- `runs consumed = 5`
- counters kept separate: yes

## Binary run vector

| Run | Launched | Frozen | 5 tests | Extent | XML | run diff | run-state | Countable |
|---|---|---|---|---|---|---|---|---|
| `run_01` | yes | yes | yes | yes | yes | yes | yes | yes |
| `run_02` | yes | yes | yes | yes | yes | yes | yes | yes |
| `run_03` | yes | yes | yes | yes | yes | yes | yes | yes |
| `run_04` | yes | yes | yes | yes | yes | yes | yes | yes |
| `run_05` | yes | yes | yes | yes | yes | yes | yes | yes |

## Business gate

| Gate | Verdict | Reason |
|---|---|---|
| persistent favorite row proved | yes | product detail creates an owned row reopened in favorites |
| sign out / sign in reopenability | yes | `run_03` proves session persistence |
| ownership separation | yes | guest and second customer do not inherit favorite ownership |
| fake breadth rejected | yes | `run_05` downgraded same-product revisit instead of selling two-product closure |
| score above 90 legal | yes | local memory allows it once a persistent favorite row is reopened |
