# Root prompt snapshot

User intent for this round:

- execute `docs/prompts/iterativ/iterative-core-standard.html`
- start a new package under `docs/out/<timestamp>`
- honor `META_ITERATION_COUNT = 10`
- use `C:\work\ex\java\demo\common\docs\html-ex\README.md` and the common `ATF5` / `ATF6` examples as calibration only
- create real tests and real run artifacts instead of stopping at report-only refinement

Cold execution boundary:

- current workspace is treated as the approved starting baseline for this round
- the previous single-run package is not reopened as active governed history
- this round must close under a new `ROOT_RUN`
