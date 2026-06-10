# Frontier Rerank Before run_10

triggering run id = run_09
why rerank is required = the negative login boundary is now cold, so the final governed slot must beat replay-only closure before run_10 starts

## Rerank Table
| Row | Chosen | Frontier | Expected T | Expected B | Proof anchor | Expected source/test delta | Expected materialization | Binary falsifier / demotion signal | Falsify ranking within one run | Decision |
|---|---|---|---|---|---|---|---|---|---|---|
| F1 | yes | registration redirect-failure boundary on the already-cold registration seam | 0.37 | 0.28 | explicit failure-context assertion around redirect-to-login and visible register-error evidence | materially expanded | materially expanded class | demote if the run lands only another replay or adds no direct failure-context assertion/evidence | yes | chosen next material frontier |
| F2 | no | replay-only closure | 0.05 | 0.03 | full-suite green replay only | none | none | demote if closure replay is narrated as business gain | yes | not a material target |
| F3 | no | live registration-success environment control | 0.82 | 0.80 | stable live redirect plus trustworthy environment ownership | materially expanded | materially expanded class | demote because environment control is still not reachable coldly from the current workspace | yes | outside this package |

heavy frontier still alive = yes
heavy frontier pressure = low
new branch candidate = registration redirect-failure boundary on the existing registration seam
why new branch beats heavy frontier = it still changes defended business/test truth locally, while replay-only closure adds none and live registration-success remains unreachable from this workspace
chosen frontier beats F1/F2/F3 = yes
expected materialization = materially expanded class
expected source/test delta = materially expanded
