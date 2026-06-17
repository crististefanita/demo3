<!-- Generated from ../html_EN/execute-and-understand-run.html. Keep source of truth in html_EN. -->
<!-- Source stylesheet: [shared-report-reference.css](../../shared-report-reference.css) -->

# Execute and Understand Run `RUN` `ARTIFACTS` `PROOF` `STATE`

- Runs the governed slot.
- Produces tests.
- Saves diff, proof, and state.
- Explains the understanding gained.
- The orchestrator decides the legality of the next move; execution works the approved slot.

Open all

Close all

## Overview

| Badge | Read here |
| --- | --- |
| `RUN` | slot approved by the orchestrator; execution and explanation happen here |
| `ARTIFACTS` | diff, proof, XML, Extent, state, and feedback saved on disk |
| `PROOF` | claim tied to reopenable proof, not just narrative text |
| `STATE` | `run-state.json`, `package-state.json`, `execution-gate-card.md` |
| `ACCOUNTING` | countability, active target, kept / partial / blocked |
| `HANDOFF` | cold memory for the next agent and for reporting |

<!-- /table -->

Owner:

run fixation

artifacts

state

Uses:

approved slot

material from wrapper

active runtime

local proof

diff

Produces:

package memory

state files

reporting handoff

Does not produce:

package legality

UI frontier

report layout

<details>
<summary>Minimum contract</summary>

| Runtime | active values are read live and become the law of the current package |
| --- | --- |
| Tests | `ROUND_TARGET_TEST_COUNT` requires countable tests: business-distinct, auditable, proof-supported |
| Runs | `META_ITERATION_COUNT` requires fully judged, cold-fixed, and explained runs |
| Separation | the two counters do not compensate for each other and do not mix |

<!-- /table -->
</details>

<details>
<summary>Common Runtime Reference — runtime, targets, diffs, state files</summary>

| Topic | What it fixes | Action here | Artifact |
| --- | --- | --- | --- |
| `agent-runtime.properties` | active values and common paths | reopen live; do not redefine locally | `execution-gate-card.md` |
| UI wrapper | specialized UI material of the slot | turn it into canonical fixation without moving the UI owner here | `ui-business-frontier-adapter.html` |
| `RUN_EXPECTED_DELTA_QUALITY` | minimum threshold for a kept run | explain kept / partial / below threshold | `score-support.md` + `run-state.json` |
| `ROUND_TARGET_TEST_COUNT` | active target of good tests per run | produce, group, count, and explain `x / target` | `run-state.json` + `quality-accounting-verdict.md` |
| `META_ITERATION_COUNT` | active target of runs per package | judge and cold-fix each slot | `package-state.json` + `execution-gate-card.md` |
| run diff | small mutation of the claim | save per run | `thin-no-index.diff` |
| root diff | the compiled package mutation | compile at package level | `root-source-thin-no-index.diff` |
| carrier | support, not primary truth | write only when the diff is not enough | optional `source-delta-carrier.md` |
| state files | the cold state of the run and package | write the state; reporting reads it | `run-state.json` + `package-state.json` + `execution-gate-card.md` |

<!-- /table -->
</details>

<details>
<summary>1. Execution boundary — artifact law, not launch law</summary>

| Owns | Does not own |
| --- | --- |
| artifact inventory
 cold fixation package
 source diff-first truth
 proof carriers
 learning MDs
 ready-for-report links | frontier legality
 allowed number of runs
 same-package continuation
 final report layout |

<!-- /table -->

- The agent freely chooses implementation, proofss, and auxiliary notes.
- Execution cere proof and lessons saved cold pe disk.
- Under-target is explained in `quality-accounting-verdict.md` and `package-close-out.md`.
- The orchestrator verifies next-move legality; execution does not launch itself.
</details>

<details open>
<summary>2. Flows and diagrams — artifact band</summary>

<details>
<summary>2.1 Quick reading map — short orientation</summary>

| If you ask | Open | You learn cold |
| --- | --- | --- |
| who fixes and who publishes? | `2.2` | runtime -> orchestrator -> execution -> reporting split |
| what must a complete run do? | `2.3` | reopen, pretrain, execute, cold fixation, handoff |
| which state files make execution verifiable? | `2.3.1` | `run-state.json`, `package-state.json`, `execution-gate-card.md` |
| when do I group multiple tests in the same run? | `2.4` | grouping rule on functional anchor |
| how do artifacts become cold-reopenable? | `2.5` | lantul diff -> proof -> learning -> handoff |

<!-- /table -->

- `2.2` = owners
- `2.3` = ciclul unui run
- `2.5` = claim -> diff -> proof -> feedback
</details>

<details open>
<summary>2.2 Runtime -> orchestrator -> execution -> reporting split — who fixes, who decides, who publishes</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 220" rolee="img" aria-label="Runtime ownership split">
  <defs><marker id="rr" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>
  <rect x="40" y="78" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
  <text x="150" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">agent-runtime.properties</text>
  <text x="150" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">thresholds + paths + libraries</text>
  <rect x="360" y="28" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
  <text x="470" y="54" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">orchestrator</text>
  <text x="470" y="76" text-anchor="middle" fill="#a8b3cf" font-size="11">legality + continuation</text>
  <rect x="360" y="124" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
  <text x="470" y="150" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">execute</text>
<text x="470" y="172" text-anchor="middle" fill="#a8b3cf" font-size="11">produce + fix + explain</text>
  <rect x="680" y="124" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
  <text x="790" y="150" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">reporting</text>
<text x="790" y="172" text-anchor="middle" fill="#a8b3cf" font-size="11">publishes frozen verdict</text>
  <rect x="1000" y="124" width="120" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
  <text x="1060" y="150" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">html final</text>
  <text x="1060" y="172" text-anchor="middle" fill="#a8b3cf" font-size="11">navigabil</text>
  <path d="M260 112 H360" stroke="#7aa2ff" stroke-width="2" marker-end="url(#rr)"></path>
  <path d="M260 112 C320 112, 300 62, 360 62" stroke="#7aa2ff" stroke-width="2" fill="none" marker-end="url(#rr)"></path>
  <path d="M580 158 H680" stroke="#7aa2ff" stroke-width="2" marker-end="url(#rr)"></path>
  <path d="M900 158 H1000" stroke="#7aa2ff" stroke-width="2" marker-end="url(#rr)"></path>
  </svg>
</div>

<details>
<summary>Legend</summary>

- Runtime fixes the common configuration; it does not decide legality or publication by itself.
- The orchestrator decides whether the next slot is legal.
- Execution produces artifacts, explains progress, and cold-fixes the package.
- Reporting only publishes the already fixed truth.
</details>
</details>

<details open>
<summary>2.3 Single run cycle — what each run must do</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 300" rolee="img" aria-label="Single run cycle">
  <defs><marker id="sr" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>
  <rect x="35" y="110" width="150" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="110" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Reopen</text><text x="110" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">history + feedback</text>
  <rect x="240" y="110" width="160" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="320" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Pretrain</text><text x="320" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">rereading frontier</text>
  <rect x="455" y="110" width="160" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="535" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Executa</text><text x="535" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">un slot guvernat</text>
<rect x="670" y="110" width="160" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="750" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Fixeaza</text><text x="750" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">diff / proof / xml</text>
  <rect x="885" y="110" width="210" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="990" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Invata + hands off</text><text x="990" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">feedback md + link-uri de report</text>
  <path d="M185 146 H240" stroke="#7aa2ff" stroke-width="2" marker-end="url(#sr)"></path><path d="M400 146 H455" stroke="#7aa2ff" stroke-width="2" marker-end="url(#sr)"></path>
  <path d="M615 146 H670" stroke="#7aa2ff" stroke-width="2" marker-end="url(#sr)"></path><path d="M830 146 H885" stroke="#7aa2ff" stroke-width="2" marker-end="url(#sr)"></path>
  </svg>
</div>

<details>
<summary>What must stay in mind</summary>

- The run must leave reopenable proof: diff, proof, state, and lesson.
- What is not saved on disk cannot be safely used by the next agent.
- Without diff, proof and state de run, fixation cold not is completa.
- A run explains the active target or publishes a defect / severe blocker.
- Decisive learning must be saved on disk, not left in chat.
</details>

<details>
<summary>2.3.1 Mandatory Artifact Spine `STATE` — minimum verifiable state</summary>

| Artefact | Owner | When it is written | missing lui blocheaza |
| --- | --- | --- | --- |
| `run-state.json` | execute | la fixation cold a run-ului | tests produced, proof and kept-status pe slot |
| `package-state.json` | execute | on each recompilation of package truth | runs consumed, breadth verdict and handoff-ul cold |
| `execution-gate-card.md` | execute writes, orchestrator verifies, reporting reads | after aggregated package fixation | cold handoff to reporting |

<!-- /table -->

| File | Canonical fields | Short enums |
| --- | --- | --- |
| `runs/run_##/review/run-state.json` | `run_id`, `tests_target`, `tests_produced`, `target_status`, `delta_quality_status`, `run_diff`, `carrier`, `proof_ui`, `proof_machine`, `kept_status`, `blocker_status` | `met / under_target / blocked / missing`, `pass / fail / n-a`, `yes / no / partial` |
| `review/package-state.json` | `runs_target`, `runs_consumed`, `run_tests_target`, `root_diff`, `winner_macro_lane`, `breadth_verdict`, `same_package_legal`, `fresh_root_run_legal`, `report_status`, `render_status`, `link_audit_status` | `broad / partial / narrow`, `yes / no`, `ready / partial / blocked / missing`, `fresh / stale / missing` |
| `review/execution-gate-card.md` | header agregat + vector pe run: `run_id`, `tests_produced`, `run_diff`, `proof`, `status` | `yes / no / partial / stale / missing`, `met / under_target / blocked` |

<!-- /table -->

```text
{
  "run_id": "run_03",
  "tests_target": "ROUND_TARGET_TEST_COUNT",
  "tests_produced": 5,
  "target_status": "met",
  "delta_quality_status": "pass",
  "run_diff": "runs/run_03/review/thin-no-index.diff",
  "carrier": "no",
  "proof_ui": "yes",
  "proof_machine": "yes",
  "kept_status": "yes",
  "blocker_status": "no"
}

{
  "runs_target": "META_ITERATION_COUNT",
  "runs_consumed": 5,
  "run_tests_target": "ROUND_TARGET_TEST_COUNT",
  "root_diff": "review/root-source-thin-no-index.diff",
  "winner_macro_lane": "orders-history",
  "breadth_verdict": "partial",
  "same_package_legal": "no",
  "fresh_root_run_legal": "yes",
  "report_status": "ready",
  "render_status": "fresh",
  "link_audit_status": "yes"
}
```

- The orchestrator defines the cold checks.
- Execution write fileele.
- Reporting only reads them.
- They do not replace the README, diff, or final report.
- Blocks defending weak execution through long prose.
</details>

<details>
<summary>2.4 Grouping rule inside the same run — when an area deserves more tests</summary>

| If you reached this point | You judge | Forma good |
| --- | --- | --- |
| page / state / functionality with distinct business behaviors | can the same anchor close the active target without a mechanical sweep? | group related tests in the same run |
| anchor that cannot support the active target | real blocker or wrong selected frontier? | publici defect / blocker sever and rerank |
| area cu alta identitate business or alta proof | separarea ajuta claritatea, izolarea or rerank-ul? | split only if the new run can close the active target |

<!-- /table -->

<details>
<summary>Legend</summary>

- Good example: same area `favorites` can produce in the same run empty-state, persistence and collection behavior, if all come from the same functional anchor.
- Bad example: five close variants of the same click or same blocker only to inflate the number.
- Good grouping is based on functionality and business truth, not implementation convenience.
- At the end of each run: state whether the active target was closed.
- If not: verdict is defect or blocker sever.
</details>
</details>

<details>
<summary>2.4.1 Execution details — before / during / after the run</summary>

| Moment | Checklist cold | Not accepta |
| --- | --- | --- |
| Before | reopen the selected pretraining, relevant artifacts, feedback, and runs that change the frontier | execution from memory or mechanically copied benchmark |
| In timpul | pursue new business, close the active target per run, group related tests on the same functional anchor | sub-target ascuns prin proza, helper-only, variante mecanice |
| After | save diff, proof, XML/Extent, state, feedback, and cold accounting | lessons left only in chat or proof without a reopenable link |

<!-- /table -->

The run closes the active target or publishes a defect / severe blocker with rerank.
</details>
</details>

<details>
<summary>2.5 Artifact chain — claim -> diff -> proof -> feedback</summary>

- `2.3.1 Mandatory Artifact Spine` = contractul.
- `2.5` = reading order.
- Do not add new artifacts.

<details>
<summary>Run Fixation Card</summary>

| Field | Short answer | Link cold |
| --- | --- | --- |
| claim | published business truth | `SUMMARY.md` |
| delta | what changed versus the previous state | `score-support.md` |
| diff | small mutation of the run | `thin-no-index.diff` |
| proof | execution confirmation | `Extent/XML/command-result` |
| state | tests produced, kept, blocker | `run-state.json` |
| feedback | the lesson for next agent | `*.md` decisiv |

<!-- /table -->
</details>

| Step | Question | You read |
| --- | --- | --- |
| claim | what business truth do you publish? | verdict scurt al run-ului |
| diff | what mutation supports the claim? | run diff for claim; root diff for sinteza |
| proof | what does execution confirm? | Extent, XML or command result |
| feedback | what does the next agent learn? | MD-ul decisiv, if schimba rerank-ul |

<!-- /table -->
</details>
</details>

<details>
<summary>3. Minimum package skeleton — small, extensible spine</summary>

```text
<timestamp>/
  README.md
  CALIBRATION.md
  manifests/package-manifest.md
  prompt/input-snapshot.md

  analysis/
    langgraph-business-understanding.html
    shared-report-reference.css
    canonical-render-check.png

  review/
    frozen-package-inventory.md
    local-link-check.txt
    quality-accounting-verdict.md
    next-run-eligibility-card.md
    package-close-out.md
    frontier-ranking-ledger.md                  (when there is competition between frontiers)
    round-pretraining-brief.md                  (fresh ROOT_RUN; same-package only if rerank changes the winner)
    *.md                                        (helper learning carriers decisive)

  runs/run_##/
    SUMMARY.md
    manifests/run-manifest.md
    prompt/input-snapshot.md
    review/artifact-checklist.md
    review/score-support.md
    review/thin-no-index.diff                   (mandatory when the run changed source/test)
    review/source-delta-carrier.md              (mandatory when the diff requires interpretation or selection)
    review/*.md                                 (feedback / blocker / repair / learning)
    validtion/command-output.txt
    validtion/command-result.md
    validtion/testng-results.xml or TEST-*.xml
    validtion/reports/ExtentReport_*.html      (execution report and reopenable proof)
```

- Minim does not mean putin valoros.
- Each mandatory file has a clear rolee.
- Each decisive file must be linked from the report.

- Run cu claim + cod/test schimbat => cere `runs/run_##/review/thin-no-index.diff`.
- Package cu more multe mutatii => cere `review/root-source-thin-no-index.diff`.
- Without a relevant diff, history is not fully fixed.

<details>
<summary>3.1 Execution Gate Card `STATE` — binary package summary</summary>

```text
execution-gate-card.md
runtime read = yes / no
memory read = yes / no
runs consumed = n / META_ITERATION_COUNT
tests produced = x / ROUND_TARGET_TEST_COUNT
root diff saved = yes / no
run_01 = tests:5 | diff:yes | proof:yes | status:met
run_02 = tests:5 | diff:yes | proof:partial | status:under_target
run_03 = tests:0 | diff:no | proof:no | status:blocked
business graph ready = yes / no
render proof = yes / no / stale
link audit = yes / no
ready for report handoff = yes / no
```

- Card scurt and binar.
- Header = aggregated package state.
- Vector pe run = target, diff, proof, status.
- Missing diff/proof remains immediately visible.
</details>
</details>

<details>
<summary>4. Artifact taxonomy — always / conditional / support</summary>

- Mandatory Artifact Spine = contractul.
- Taxonomy = artifact severity.
- Not muta ownership-ul.

| Level | Enters here | Cold rule |
| --- | --- | --- |
| mereu | diff relevant, proof relevant, state files, close-out | without them, the run or package is not fully fixed |
| conditionat | `source-delta-carrier.md`, blocker/repair/frontier MD, specialized subsection | become mandatory when they change the claim, interpretation, or rerank |
| support | note scurte, explicatii locale, context auxiliar | se pastreaza only if ajuta Reopenrea cold |

<!-- /table -->

- Not salva artifacts ca decor.
- Keep only truth, necessary interpretation, or real support.
- If not ajuta next agent, not enters in fixation cold.
</details>

<details>
<summary>5. Diff-first rule for evolution `PROOF` — git diff explains iterative evolution</summary>

| If | You save | Rol |
| --- | --- | --- |
| a single run changed the published claim | `runs/run_##/review/thin-no-index.diff` | small mutation of the run |
| more multe runs au schimbat package | `review/root-source-thin-no-index.diff` | the compiled package mutation |
| the diff alone does not clearly explain the claim | `source-delta-carrier.md` | interpretation support, not primary truth |

<!-- /table -->

```text
pe run
  runs/run_##/review/thin-no-index.diff = small truth of the run claim
  runs/run_##/review/source-delta-carrier.md = support only if the diff is not enough
  SUMMARY.md = 1-3 lines about the historical mutation of the run

per package
  review/root-source-thin-no-index.diff = the compiled package mutation
  the report uses run diff for the small claim and root diff for the large synthesis
```

- The diff is the primary truth of evolution.
- File snapshots are support.
- Agent explanations are support.

- `source-delta-carrier.md` explains the selection or meaning of the diff.
- If the diff is missing, the carrier does not save the claim.

- `SUMMARY.md`: 1-3 lines per run.
- State the historical mutation versus the previous frontier.
- The diff remains the primary truth.
- The summary only makes it quick to reopen.
</details>

<details>
<summary>6. Extent as execution report `PROOF` — steps, status, screenshots when they exist</summary>

| Caz | What se cere | Impact if missing |
| --- | --- | --- |
| run kept / proof-bearing | `ExtentReport_*.html` directly reopenable, when it exists for that run | trust drops or the claim becomes only partially auditable |
| UI or flow vizual | Extent with steps, screenshots, or visible anchors | visual proof missing from the published chain |
| failure / blocker | Extent, XML or command output cu failure relevant | blocker-ul remains opinie |
| final report | link direct in Run Audit Strip and Artifact Index | report incomplet |

<!-- /table -->

- Extent not is only UI.
- For UI: steps and images.
- For execution: reopenable run report.
- Rol: sustine claim-uri proof-bearing.
</details>

<details>
<summary>7. Feedback constructive per run `HANDOFF` — maximum self-learning</summary>

| Tip feedback | File recomandat | Continut |
| --- | --- | --- |
| business learning | `business-understanding.md` | what business a devenit more clar |
| frontier decision | `frontier-decision.md` | de what am ales or pivotat branch-ul |
| blocker | `blocker-analysis.md` | what blocheaza proof and what input missing |
| repair | `repair-analysis.md` | what support was repaired and what it unlocks |
| lesson | `lesson-learned.md` | a short lesson for the next agent |

<!-- /table -->

- Do not publish every score.
- Publish every score that influences the verdict.
- Everything that influences the verdict must be linked from the report.
</details>

<details>
<summary>8. Rigid minimum templates `ACCOUNTING` — few, but firm</summary>

```text
artifact-checklist.md
run frozen = yes / no
business claim settled = yes / no
thin-no-index.diff saved = yes / no / n/a
if no, reason = ...
source-delta-carrier required = yes / no
if yes, why = ...
run diff linked from SUMMARY/report = yes / no
ExtentReport saved = yes / no / n/a
machine result carrier saved = yes / no
decisive learning md saved = yes / no
countable kept run = yes / no
missing mandatory artifact = yes / no
notes = short only

quality-accounting-verdict.md
ROUND_TARGET_TEST_COUNT numeric = x / target
accounting target met = yes / no
business breadth verdict = broad / partial / narrow
how many good tests this run produced = ...
why these tests belong together in the same run = ...
why this run stayed below configured ambition if it did = ...
why countable = ...
why breadth is still narrow if needed = ...
what next frontier must add = ...

lesson-learned.md
what changed = ...
what was learned = ...
what should not be repeated = ...
what next run should use = ...
links = ...

frontier-decision.md
candidate frontier = ...
why it is new business = ...
why it beats alternatives = ...
required proof = ...
fallback / blocker = ...
```
</details>

function toggleAll(openState){document.querySelectorAll('details').forEach(function(el){el.open=openState;});}
