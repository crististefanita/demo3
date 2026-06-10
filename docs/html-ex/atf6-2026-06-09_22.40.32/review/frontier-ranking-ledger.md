# Frontier Ranking Ledger

## 30-Second Decision Row

F# | chosen? | beats now | loses now | single-run falsifier artifact + fail signal | same-round consequence
---|---|---|---|---|---
F1 | yes | beats F2 and F3 while parent truth is unresolved | loses to nothing upstream inside this round | `RegistrationTests XML + Extent`: fail if successful registration still stalls on the same page | if F1 fails, F2 becomes illegal and F3 loses on dependency
F2 | yes, but only if F1 passes | beats F3 as the last spend inside the chosen parent chain | loses to F1 if parent truth is still unresolved | `AuthenticatedAccountTests XML + Extent`: fail if login continuity breaks or overview identity is absent | if F2 fails, F3 cannot inherit same-round priority
F3 | no in this round | beats no sibling breadth candidate yet | loses as a same-round spend because no cold reranking exists across profile form, favorites, invoices, and messages | chosen breadth-slice XML + Extent would falsify only slice-local success, not ranking legitimacy | after F2 closes, F3 becomes fresh-round only

## Frontier Inventory

Candidate | Surface | Unexplored claim | Business weight | Expected T truth delta | Expected B branch-depth delta | Proof anchor | Expected test/support delta | Binary observable demotion condition
---|---|---|---|---|---|---|---|---
F1 | public registration parent branch | successful registration still stalls vs now redirects to login | high | high | medium | `runs/run_02/validation/TEST-com.endava.ai.atf.ui.RegistrationTests.xml` | none; live rerun only | if the rerun still stalls on register, F1 loses; F2 becomes illegal; F3 loses on dependency
F2 | authenticated account overview | successful public registration can carry one governed descendant after login | high | medium | high | `runs/run_03/validation/TEST-com.endava.ai.atf.ui.AuthenticatedAccountTests.xml` | one new descendant test plus account/login support slice | if login continuity fails or overview identity is absent, F2 loses; F3 cannot inherit same-round priority
F3 | broader authenticated descendants | the package can own profile form, favorites, invoices, or messages | high | medium | high | reranked chosen-slice XML + Extent pair, not inherited from `run_03` | materially expanded support for the chosen descendant slice | if F2 is not already owned, F3 loses as premature breadth; if F2 is owned, F3 still loses same-round legitimacy until sibling breadth candidates are reranked cold

## Ranking

Rank | Candidate | Why now | Why not the heavier alternative | Expected novelty class | Comfort-branch risk | Falsify ranking within one run: artifact family + pass/fail signal
---|---|---|---|---|---|---
1 | F1 | beats every downstream option while parent truth is unresolved | F2 and F3 both lose until the parent truth is settled | closure-only | low | parent XML + Extent: redirect = pass; stall = fail
2 | F2 | beats F3 only after F1 passes because it is still the same chain and still has one-step falsification | F3 is heavier, but after F1 the cold next spend is one descendant proof, not breadth competition | deeper-branch | medium | descendant XML + Extent: overview + persisted identity = pass; auth break = fail
3 | F3 | stays the heaviest open frontier after run_03 | loses same-round best-spend status because it has not beaten sibling breadth candidates under fresh pressure and cannot inherit `F1 -> F2` momentum honestly | deeper-branch | high | fresh chosen-slice XML + Extent after reranking; otherwise the ranking is unproven

## Launch Verdict

- heavy frontier still alive = yes
- chosen frontier = `F1`, then `F2` only if `F1` passes
- chosen frontier beats `F1/F2/F3` = yes
- expected materialization = `none` for `F1`, then `materially expanded class` for `F2`; `F3` must earn its own materialization class only after fresh reranking
- expected source/test delta = `F1 -> none with reason`; `F2 -> materially expanded`; `F3 -> deferred to fresh round`
- expected changed tests/support = `F1 -> none`; `F2 -> account overview page/service/validation + one new descendant test`; `F3 -> deferred descendant-specific slice`
- launch decision = `launch`
- why `run_04` was not the best same-round spend = after `run_03`, no unresolved claim remained inside `F1 -> F2`; the next decision was a fresh ranking contest among sibling breadth candidates
- same-round continuation would have been wrong because = it would have mislabeled a new frontier launch as chain continuation and would have spent budget before cold reranking reopened
- procedural risk prevented by stopping = one arbitrary breadth slice could inherit settled chain credit and look stronger than it actually was
- what would have been weak if forced = a same-round `run_04` could prove only slice-local success; it could not prove that the chosen slice still beat the other open breadth candidates procedurally
