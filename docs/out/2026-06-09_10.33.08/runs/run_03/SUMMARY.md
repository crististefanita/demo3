# Run_03 Summary

## Winner Truth

- A syntactically valid NL postal and house pair still materializes a populated address instead of collapsing immediately.
- The branch therefore exposes a permissive lookup boundary, not only happy-path autofill.

## Why It Matters

- This run keeps the new workflow honest by naming a strange but real boundary.
- It prevents the package from reading as if only canonical address pairs were tested.

## Countability

- countable kept run = yes
- business branch = public registration synthetic-input materialization boundary
- result = 1 passed / 0 failed / 0 skipped