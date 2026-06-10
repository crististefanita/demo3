# Frontier Rerank After run_05

triggering run id = run_05
why rerank is required = the base authenticated chain is now cold, so the strongest next frontier changed and mid-window rerank became mandatory before another governed run

## Rerank Table
| Row | Chosen | Frontier | Expected T | Expected B | Proof anchor | Expected source/test delta | Expected materialization | Binary falsifier / demotion signal | Falsify ranking within one run | Decision |
|---|---|---|---|---|---|---|---|---|---|---|
| F1 | yes | favorites sibling from account surface | 0.51 | 0.58 | explicit favorites support plus green contract path from account | new | new class | demote if favorites remains seed-only memory or if no new service/test support lands | yes | chosen next material frontier |
| F2 | no | negative login boundary at entry | 0.46 | 0.44 | explicit stay-on-login plus login-error proof | new | materially expanded class | demote now because it closes entry semantics but adds less business width than the first downstream sibling after the chain is already cold | yes | hold for later |
| F3 | no | replay-only closure | 0.08 | 0.06 | full-suite green replay only | none | closure-only | demote if it is described as frontier gain instead of corroboration | yes | allowed only as corroboration, not as the next material target |

heavy frontier still alive = yes
heavy frontier pressure = medium
new branch candidate = favorites sibling from account surface
why new branch beats heavy frontier = after run_05, the first downstream sibling widens business scope more than the negative entry boundary, while replay-only closure adds no new truth
chosen frontier beats F1/F2/F3 = yes
expected materialization = new class
expected source/test delta = new

closure-only allowance before pivot = one corroboration replay may be spent, but it does not replace the published next material target
