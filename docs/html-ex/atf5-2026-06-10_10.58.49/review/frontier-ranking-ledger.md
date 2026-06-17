# Frontier Ranking Ledger

## 30-Second Decision Row
| Row | Chosen | Frontier | Expected T | Expected B | Proof anchor | Expected source/test delta | Expected materialization | Binary falsifier / demotion signal | Falsify ranking within one run | Decision |
|---|---|---|---|---|---|---|---|---|---|---|
| F1 | yes | authenticated continuation local slice from `Example.java` | 0.86 | 0.82 | new login/account/profile/password support plus green component contracts | new | new class | demote if the slice stays seed prose only and no layered ATF support lands | yes | launch |
| F2 | no | controllable live registration-success environment | 0.90 | 0.86 | live redirect proof plus stable environment control | materially expanded | materially expanded class | demote for this round because environment control is not reachable coldly from the current workspace | yes | strengthen first if chosen now |
| F3 | no | favorites-only sibling branch from seed | 0.42 | 0.50 | new sibling support plus local proof | new | new class | demote if the base authenticated slice is still unowned, or if any heavier reachable frontier still owns the budget | yes | comfort branch at launch |

## Frontier Inventory
| Candidate | Surface | Unexplored claim | Business weight | Expected T truth delta | Expected B branch-depth delta | Proof anchor | Expected source/test delta | Expected materialization | Binary observable demotion condition |
|---|---|---|---|---|---|---|---|---|---|
| F1 | login -> account -> profile -> password change | seed-authenticated continuation can be translated into layered ATF support and cold local proof | very high | 0.86 | 0.82 | `Example.java` plus green component contracts | new | new class | demote if the run finishes without new page/service/validation/test layers |
| F2 | live registration success control | the old external blocker could become controllable enough for live descendant authority | very high | 0.90 | 0.86 | stable live redirect plus trustworthy rerun | materially expanded | materially expanded class | demote if environment control is still absent and only old blocker truth is replayed |
| F3 | favorites sibling branch | one lighter descendant could be translated only after auth base ownership exists and only after a fresh rerank beats heavier remaining pressure | medium | 0.42 | 0.50 | `Example.java` plus local support slice | new | new class | demote if login/account/profile base remains incomplete or if a heavier frontier still governs budget |

## Ranking
| Rank | Frontier | Why it sits here | What beats or loses to it | Novelty class if chosen | Risk | One-run falsifier |
|---|---|---|---|---|---|---|
| 1 | F1 | strongest reachable unexplored branch inside current repo state | beats F2 because F2 still depends on environment control; beats F3 because F3 is downstream of auth base ownership | frontier-expanding | medium | if no layered slice lands, ranking was wrong |
| 2 | F2 | heavier business authority but not coldly reachable right now | loses because it still depends on missing controllable live registration success | deeper-branch | high | if environment control appears quickly, rerank upward next round |
| 3 | F3 | viable only after F1 establishes auth base grammar | loses because it would skip the core authenticated continuation seam and does not beat heavier pressure at launch | sibling-variant | medium | if F1 stalls and F3 lands deeper proof faster, rerank later |

## Launch Verdict
heavy frontier still alive = yes
chosen frontier = F1
chosen frontier beats alternatives this run = yes
expected novelty class = frontier-expanding
expected materialization = new class
expected source/test delta = new
expected changed tests/support = `AccountPage.java`, `LoginService.java`, `LoginValidation.java`, `AuthenticatedContinuationContractTest.java`
launch decision = launch
comfort-branch guard = F3 before F1 closure would be invalid; F3 after F1 closure still requires a fresh rerank against remaining heavier pressure
