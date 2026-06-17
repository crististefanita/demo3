# Frontier Ranking Ledger

- extra artifact carries published claim = `yes`
- named HTML owner = `5.5.3 #s553`
- scope of this ledger = authenticated sibling family under `/account`, but it under-scoped the visible profile subflows that were already present on the proved page
- wider visible site opportunities left unranked here = profile update submit, password change with relogin, favorites activation from product listing, contact-to-messages generation, and order flow that could later feed invoices

## 30-Second Decision Row

F# | chosen? | beats now | loses now | single-run falsifier artifact + fail signal | same-round consequence
---|---|---|---|---|---
F1 | yes | beats F2 and F3 because it proves persisted registered-user data, not just authenticated reachability | loses to nothing upstream inside this fresh round | `AuthenticatedProfileTests XML + Extent`: fail if profile values are not prefilled from the just-created registration or update CTA is absent | if F1 fails, the breadth rerank must reopen before any sibling can claim priority
F2 | no in this round | beats low-yield empty-state surfaces only if profile loses on persisted-user proof | loses to F1 because empty favorites reachability is weaker than multi-field identity persistence | favorites XML + Extent: fail if authenticated favorites surface does not load or yields only trivial nav reachability | if F2 ran before F1, the round would spend on a weaker falsifier
F3 | no in this round | beats only lower-value empty-state breadth after a new rerank | loses to F1 because invoices do not reuse the just-created registration data as strongly as profile does | invoices XML + Extent: fail if authenticated invoices surface does not load, but pass would still be weaker than F1 on user-data persistence | if F3 ran before F1, the round would choose convenience over strongest proof

## Frontier Inventory

Candidate | Surface | Unexplored claim | Business weight | Expected T truth delta | Expected B branch-depth delta | Proof anchor | Expected test/support delta | Binary observable demotion condition
---|---|---|---|---|---|---|---|---
F1 | authenticated profile form | a just-registered user can open profile and find persisted registration values plus update CTA | high | high | medium | `runs/run_01/validation/TEST-com.endava.ai.atf.ui.AuthenticatedProfileTests.xml` | materially expanded support for one sibling slice | if any core profile field or the update CTA is missing, F1 loses and the sibling rerank must reopen
F2 | authenticated favorites surface | a just-registered user can reach favorites with branch-local meaning | medium | medium | medium | chosen favorites XML + Extent pair | materially expanded support for one sibling slice | if favorites yields only trivial nav reachability or an empty surface with no branch-specific meaning, F2 loses
F3 | authenticated invoices surface | a just-registered user can reach invoices with honest branch-local meaning | medium | low | medium | chosen invoices XML + Extent pair | materially expanded support for one sibling slice | if invoices yields only trivial nav reachability or empty-state proof weaker than profile, F3 loses

## Ranking

Rank | Candidate | Why now | Why not the heavier alternative | Expected novelty class | Comfort-branch risk | Falsify ranking within one run: artifact family + pass/fail signal
---|---|---|---|---|---|---
1 | F1 | profile is the strongest sibling because it can falsify persisted identity and address continuity across multiple fields in one run | favorites and invoices would only prove weaker branch-local reachability for a fresh user | sibling-variant | low | profile XML + Extent + command output: prefilled values + update CTA = pass; any missing persisted field = fail
2 | F2 | favorites is still reachable, but its likely empty-state meaning is weaker than profile persistence | F1 already beats it on deterministic proof strength | sibling-variant | medium | favorites XML + Extent: meaningful authenticated favorites surface = pass; trivial empty state = fail
3 | F3 | invoices remains open as another breadth slice | it loses to F1 on persisted-user proof and to F2 on more obvious user-facing value | sibling-variant | medium | invoices XML + Extent: meaningful authenticated invoices surface = pass; trivial empty state = fail

## Launch Verdict

- heavy frontier still alive = yes
- chosen frontier = `F1`
- chosen frontier beats `F1/F2/F3` = yes
- expected novelty class = `sibling-variant`
- expected materialization = `materially expanded class`
- expected source/test delta = materially expanded support and one new governed descendant test for authenticated profile persistence
- expected changed tests/support = `ProfilePage`, `ProfileService`, `ProfileValidation`, and `AuthenticatedProfileTests`
- launch decision = `launch`
- why not `F2` or `F3` first = they offer weaker single-run falsifiers for a fresh user than profile persistence does
- same-round continuation would be wrong because = after the profile win, the remaining siblings need a new cold rerank rather than inherited priority
- procedural risk prevented by stopping = one sibling breadth slice would otherwise look stronger than it earned just because the first breadth winner already passed
- next honest target settled coldly = no
- why next frontier row cannot be copied forward mechanically = the ledger chose the first breadth winner honestly, but it did not coldly reconcile profile update submit and password change with relogin against the remaining sibling breadth candidates
- strategic cap of this package = the ledger chose the strongest first sibling winner, but it did not maximize broader application understanding across richer profile subflows and adjacent feeder families
