# Frontier Rerank Before run_09

triggering run id = run_08
why rerank is required = the sibling branch is already cold and the next material spend must beat replay-only closure and any lighter downstream continuation before run_09 starts

## Rerank Table
| Row | Chosen | Frontier | Expected T | Expected B | Proof anchor | Expected source/test delta | Expected materialization | Binary falsifier / demotion signal | Falsify ranking within one run | Decision |
|---|---|---|---|---|---|---|---|---|---|---|
| F1 | yes | negative login boundary at entry | 0.49 | 0.41 | explicit stay-on-login and login-error proof | new | materially expanded class | demote if the run adds no direct negative-login assertion or still lands only happy-path replay | yes | chosen next material frontier |
| F2 | no | another authenticated sibling or downstream auth leaf | 0.31 | 0.37 | extra descendant support beyond favorites | materially expanded | materially expanded class | demote because another local auth leaf would widen laterally but would still leave entry failure semantics unowned | yes | loses on truth balance |
| F3 | no | replay-only closure | 0.07 | 0.05 | full-suite green replay only | none | closure-only | demote if closure replay is narrated as business gain | yes | not a material target |

heavy frontier still alive = yes
heavy frontier pressure = medium
new branch candidate = negative login boundary at entry
why new branch beats heavy frontier = it closes the missing bidirectional truth on the login seam, while another auth leaf is lighter and replay-only closure adds no new learning
chosen frontier beats F1/F2/F3 = yes
expected materialization = materially expanded class
expected source/test delta = new
