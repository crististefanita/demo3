<!-- Generated from ../html_EN/orchestrate-iterative-runs.html. Keep source of truth in html_EN. -->
<!-- Source stylesheet: [shared-report-reference.css](../../shared-report-reference.css) -->

# Orchestrate Iterative Runs `RUNTIME` `LEGALITY` `RUN WINDOW` `HANDOFF`

- Drives the iterative package and the run window.
- Judges continuation, stop, and fresh ROOT_RUN legality.
- Execution produces artifacts. Reporting publishes the final truth.

Open all

Close all

## Overview

| Badge | Read here |
| --- | --- |
| `RUNTIME` | active values and common paths read from `agent-runtime.properties` |
| `LEGALITY` | launch, continuation, stop, severe blocker, fresh ROOT_RUN |
| `RUN WINDOW` | package slots and the separation between produced tests and consumed runs |
| `HANDOFF` | handoff to the pretraining owner, run wrapper, and reporting |
| `INVALID` | mixed targets, premature stop, or reporting used as legality |

<!-- /table -->

Owner:

package orchestrator

move decision

run window

Uses:

active runtime

local memory

audited pretraining

Delegates:

pretraining to its owner

run to wrapper

publication to reporting

Does not produce:

tests

run artifacts

report layout

<details>
<summary>Minimum contract — 3 lines, closed by default</summary>

| Axis | Cold rule |
| --- | --- |
| Runtime | active values are read live and are not negotiated locally |
| Counters | `ROUND_TARGET_TEST_COUNT` = tests per run; `META_ITERATION_COUNT` = runs per package |
| Below value | procedural defect or severe blocker documented in state + gate card |

<!-- /table -->
</details>

## Common Runtime Reference

<details>
<summary>Active constants — reference closed by default</summary>

| Constant | Measures | Owner | Artifact that fixes it | If missing or below it |
| --- | --- | --- | --- | --- |
| `RUN_EXPECTED_DELTA_QUALITY` | minimum quality threshold for a kept run | the orchestrator judges; execution explains the support | `score-support.md` + `quality-accounting-verdict.md` | run below threshold or cold learning / rerank verdict |
| `ROUND_TARGET_TEST_COUNT` | how many good auditable tests each run must leave behind | wrapper / execute produce; the orchestrator judges under-target | `run-state.json` + `quality-accounting-verdict.md` | procedural defect or severe blocker documented on the run |
| `META_ITERATION_COUNT` | how many runs must be consumed in the package | the orchestrator requires each slot consumed or severely closed; the run goes through wrapper and execute | `package-state.json` + `execution-gate-card.md` | package under-spend: procedural defect or severe blocker documented |

<!-- /table -->
</details>

<details open>
<summary>1. Owner Map — who decides what</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 260" rolee="img" aria-label="Cold truth chain for business understanding">
<defs><marker id="tc" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>
<rect x="20" y="84" width="172" height="76" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="106" y="112" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">agent-runtime</text>
<text x="106" y="136" text-anchor="middle" fill="#a8b3cf" font-size="11">quality / target / paths</text>
<rect x="232" y="84" width="172" height="76" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="318" y="112" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Orchestrator</text>
<text x="318" y="136" text-anchor="middle" fill="#a8b3cf" font-size="11">legality over the run window</text>
<rect x="444" y="84" width="172" height="76" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="530" y="112" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Run adapter</text>
<text x="530" y="136" text-anchor="middle" fill="#a8b3cf" font-size="11">UI now / API later</text>
<rect x="656" y="84" width="172" height="76" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="742" y="112" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Artifacts</text>
<text x="742" y="136" text-anchor="middle" fill="#a8b3cf" font-size="11">diff / Extent / XML / MD</text>
<rect x="868" y="84" width="250" height="76" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="993" y="112" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Final business report</text>
<text x="993" y="136" text-anchor="middle" fill="#a8b3cf" font-size="11">navigable graph + proof</text>
<path d="M192 122 H232" stroke="#7aa2ff" stroke-width="2" marker-end="url(#tc)"></path><path d="M404 122 H444" stroke="#7aa2ff" stroke-width="2" marker-end="url(#tc)"></path>
<path d="M616 122 H656" stroke="#7aa2ff" stroke-width="2" marker-end="url(#tc)"></path><path d="M828 122 H868" stroke="#7aa2ff" stroke-width="2" marker-end="url(#tc)"></path>
</svg>
</div>

<details>
<summary>1.1 Owner / delegation matrix — roles and handoffs</summary>

| Owner | Role | Delegation |
| --- | --- | --- |
| `orchestrate-iterative-runs.html` | legality, values active, rerank, continuation/stop, handoff | to adapter for 1 run or reporting for final |
| `docs/Living_Architecture_UI_API_doc_v1_0.html` | ATF structure, UI/API architecture, layers, configuration, technical reporting | structurel reference for discovery and implementation; does not decide legality, ranking, or final truth |
| `pretraining-reference.html` | owner for sources, order cold, intake card and anti-copy rules | to orchestrator: audited intake, artifact saved and cold decision |
| `ui-business-frontier-adapter.html` | UI wrapper for a single run; adds actor, intent, anchors, and local verdict | the wrapper hands off slot material to `execute-and-understand-run.html` |
| `execute-and-understand-run.html` | canonical fixation, artifacts, diff, proof, MD learning, package memory | to reporting for publication |
| `reporting.html` | publishes the final navigable truth | does not rejudge legality |

<!-- /table -->
</details>

<details>
<summary>Legend</summary>

- `orchestrate-iterative-runs` decides legality and the shape of the next move.
- `Living_Architecture_UI_API_doc_v1_0.html` decides ATF structure and good practices, not the iterative verdict.
- `reporting.html` publishes the final business truth, with all main sections collapsible; the slot `4. Business Flow Graph` remains the only one open by default.
</details>
</details>

<details open>
<summary>2. Flows and diagrams — orchestrator work band</summary>

<details>
<summary>2.1 Quick reading map — short orientation</summary>

| If you ask | Open | Learn cold |
| --- | --- | --- |
| what is the minimum package order? | `2.2` | short state machine, without long prose |
| what does the complete package window look like? | `2.3` | slots, fixation, and the decision for the next step |
| what do you extract and in what order do you reread? | `pretraining-reference.html` | sources, order, intake card, and what not to inherit |
| what does the orchestrator check before launch? | `2.4` | short gate: reread owner, audited intake, frontier winner cold |
| how do you separate same-package from fresh ROOT_RUN? | `2.7` | legality distinctions that must not be mixed |
| what does reporting receive, and only that? | `2.8` | minimum handoff, already frozen |

<!-- /table -->

- `2.2` = minimum order.
- `2.3` = full window.
- `2.4` = pretraining gate.
- Sources and order live in `pretraining-reference.html`.
</details>

<details>
<summary>2.1.1 Canonical legal-move card — context -> legal move -> artifact</summary>

| Context | Legal move | Required artifact |
| --- | --- | --- |
| slot ramas in package | launch a valuable run or document a severe blocker | `execution-gate-card.md` |
| the same-package frontier still wins | same-package continuation | `next-run-eligibility-card.md` |
| another family becomes more valuable | fresh ROOT_RUN after pretraining | `round-pretraining-brief.md` / audited intake |
| incomplete fixation | do not continue and do not start fresh | `package-state.json` + close-out cold |

<!-- /table -->

- This card decides the move.
- Source details live in `pretraining-reference.html`.
- Artifact details are produced after the wrapper, in `execute-and-understand-run.html`.
</details>

<details>
<summary>2.2 Minimum package order — short state machine</summary>

```text
read_runtime
-> read_memory
-> pretrain
-> run_slot_01 .. run_slot_N
-> fix_slot
-> next_slot_decision
-> package_closeout
-> report_handoff
-> memory_writeback
```

| Node | If missing, do not move forward |
| --- | --- |
| `read_runtime` | active values, paths, and common references |
| `read_memory` | `docs/out/README.md` and the latest relevant local package |
| `pretrain` | audited intake, anti-copy and cold decision saved |
| `run_slot` | slot delegat wrapper-ului; wrapper-ul hands off materia to execute |
| `next_slot_decision` | legal continuation or severe blocker for the remaining slot |
| `package_closeout` | `package-state.json` + close-out cold |
| `report_handoff` | artifacts suficiente for final page |

<!-- /table -->
</details>

<details open>
<summary>2.3 Package lane — active window, each slot with proof</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 310" rolee="img" aria-label="N-run orchestrator lane">
<defs><marker id="co" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>
<rect x="30" y="110" width="145" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="102" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Cold reread</text><text x="102" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">history + artifacts</text>
<rect x="220" y="110" width="145" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="292" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Rerank</text><text x="292" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">new frontier</text>
<rect x="410" y="110" width="150" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="485" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Launch run</text><text x="485" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">to wrapper</text>
<rect x="605" y="110" width="150" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="680" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Wrapper</text><text x="680" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">hands off to execute</text>
<rect x="800" y="110" width="145" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="872" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Judge</text><text x="872" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">kept / countable?</text>
<rect x="990" y="110" width="135" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="1057" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Report</text><text x="1057" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">only at the end</text>
<path d="M175 146 H220" stroke="#7aa2ff" stroke-width="2" marker-end="url(#co)"></path><path d="M365 146 H410" stroke="#7aa2ff" stroke-width="2" marker-end="url(#co)"></path><path d="M560 146 H605" stroke="#7aa2ff" stroke-width="2" marker-end="url(#co)"></path><path d="M755 146 H800" stroke="#7aa2ff" stroke-width="2" marker-end="url(#co)"></path><path d="M945 146 H990" stroke="#7aa2ff" stroke-width="2" marker-end="url(#co)"></path>
<path d="M872 182 C835 260 292 260 292 182" stroke="#4b5f82" stroke-width="2" fill="none" stroke-dasharray="6 5" marker-end="url(#co)"></path>
<text x="580" y="270" text-anchor="middle" fill="#a8b3cf" font-size="12">repeat only while continuation remains legal; lack of value must be proven through a severe blocker</text>
</svg>
</div>

- Do not leave unjudged slots.
- After each run fixation, recalculate the remaining slots up to `META_ITERATION_COUNT`.
- Cold decision: run legally consumable or severe blocker documented.

<details>
<summary>How to read the lane</summary>

- Do not skip the reread: without memory and runtime, the rerank starts warm.
- Do not confuse thresholds: quality is judged per run, while the window is judged per package.
</details>
</details>

<details>
<summary>2.4 Pretraining gate — the orchestrator consumes the audited artifact, not the doctrine</summary>

- Complete owner: `pretraining-reference.html`.
- The orchestrator verifies only that pretraining was executed and saved auditably.
- The cold decision must be able to govern launch / rerank / stop.

| The orchestrator verifies | Must exist | If missing |
| --- | --- | --- |
| owner open | `pretraining-reference.html` reread | do not launch the run |
| brief closed | `round-pretraining-brief.md` for fresh ROOT_RUN or rerank same-package which changes the winner | warm / non-auditable rerank |
| selected frontier | `frontier-ranking-ledger.md` or equivalent verdict | no cold winner exists |
| no-copy risk cut | what is not inherited from memory/html-ex | the benchmark can contaminate business understanding |

<!-- /table -->

```text
pretraining owner = pretraining-reference.html
orchestrator consumes = audited intake + cold decision + artifacts
orchestrator does not own = source doctrine, benchmark interpretation, anti-copy rules
```
</details>

<details>
<summary>2.6 Business novelty acquisition — what the orchestrator maximizes</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 300" rolee="img" aria-label="Business novelty acquisition">
<defs><marker id="bf" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>
<rect x="40" y="94" width="220" height="72" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="150" y="123" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Baseline covered</text>
<text x="150" y="146" text-anchor="middle" fill="#a8b3cf" font-size="11">already defended</text>
<rect x="350" y="94" width="220" height="72" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="460" y="123" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">New frontier</text>
<text x="460" y="146" text-anchor="middle" fill="#a8b3cf" font-size="11">new business</text>
<rect x="700" y="20" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="810" y="46" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Owned / countable</text>
<text x="810" y="67" text-anchor="middle" fill="#a8b3cf" font-size="11">enters in x / target</text>
<rect x="700" y="104" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="810" y="130" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Partial / repair-only</text>
<text x="810" y="151" text-anchor="middle" fill="#a8b3cf" font-size="11">useful support</text>
<rect x="700" y="188" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="810" y="214" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Blocked</text>
<text x="810" y="235" text-anchor="middle" fill="#a8b3cf" font-size="11">does not hold cold</text>
<rect x="940" y="104" width="180" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="1030" y="130" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Only fresh-round</text>
<text x="1030" y="151" text-anchor="middle" fill="#a8b3cf" font-size="11">another package</text>
<path d="M260 130 H350" stroke="#7aa2ff" stroke-width="2" marker-end="url(#bf)"></path>
<path d="M570 130 C620 118 640 54 700 54" stroke="#7aa2ff" stroke-width="2" fill="none" marker-end="url(#bf)"></path>
<path d="M570 130 C625 130 640 138 700 138" stroke="#7aa2ff" stroke-width="2" fill="none" marker-end="url(#bf)"></path>
<path d="M570 130 C620 142 640 222 700 222" stroke="#7aa2ff" stroke-width="2" fill="none" marker-end="url(#bf)"></path>
<path d="M570 130 C700 130 790 138 940 138" stroke="#7aa2ff" stroke-width="2" fill="none" marker-end="url(#bf)"></path>
</svg>
</div>

```text
run diff = small mutation
root diff = compiled mutation
carrier = support only if the diff is not enough
```

<details>
<summary>Legend</summary>

- `owned / countable` = new business identity, supported by diff + proof.
- `partial / repair-only` = better support or better reachability, without new cold business.
- `blocked` = frontier does not hold cold, even if intuition or the interactive browser looked positive.
- `fresh-round-only` = valid frontier, but legally moved into another package.
</details>

| Orchestrator question | Decision | Expected artifact |
| --- | --- | --- |
| What business was already covered? | do not spend a slot on it except for necessary repair | baseline score / reference to existing test |
| Which new frontier is most promising? | rank and choose a candidate | `frontier-ranking-ledger.md` |
| What did the previous run learn? | continue, pivot, or stop | helper MD + next-run-eligibility-card |
| What entered x / ROUND_TARGET_TEST_COUNT? | countable only if it has business identity + proof + sufficiently distinct cold contribution | quality-accounting-verdict |

<!-- /table -->

- 5 payment methods can be numerically sufficient and business-narrow.
- 5 similar recoveries can be numerically sufficient and business-narrow.
- 5 parameterized variants can tick the target without real breadth.
- Block the false reading: a ticked number does not mean a good package.
</details>

<details>
<summary>2.7 Same-package vs fresh ROOT_RUN — legality distinctions</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 220" rolee="img" aria-label="Same package versus fresh root">
<defs><marker id="sf" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>
<rect x="30" y="78" width="200" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="130" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">run_n fixed</text>
<text x="130" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">complete fixation</text>
<rect x="300" y="78" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="410" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">same-package legal?</text>
<text x="410" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">frontier same-round more good?</text>
<rect x="590" y="78" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="700" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">fresh ROOT_RUN legal?</text>
<text x="700" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">another family, another package</text>
<rect x="880" y="78" width="230" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="995" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">launch-ready now?</text>
<text x="995" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">prepare + pretrain real</text>
<path d="M230 112 H300" stroke="#7aa2ff" stroke-width="2" marker-end="url(#sf)"></path>
<path d="M520 112 H590" stroke="#7aa2ff" stroke-width="2" marker-end="url(#sf)"></path>
<path d="M810 112 H880" stroke="#7aa2ff" stroke-width="2" marker-end="url(#sf)"></path>
</svg>
</div>

<details>
<summary>Legend</summary>

- `same-package blocked` does not mean `application exhausted`.
- `fresh ROOT_RUN legal` does not automatically mean `launch-ready now`.
- Fresh-round legality comes from cold handoff; launch-ready comes after real prepare + pretrain.
- If an agent confuses these three verdicts, the next package starts from false memory.
- `META_ITERATION_COUNT` requires consuming the whole active window; any closure below it must be treated as a defect or severe blocker.
- Before the first code step, the brief must explicitly state how you will reach `tests produced = x / ROUND_TARGET_TEST_COUNT` and `runs consumed = n / META_ITERATION_COUNT`.
</details>

<details>
<summary>2.7.1 Final single decision card</summary>

| Question | Accepted answer | Minimum proof |
| --- | --- | --- |
| does a heavier same-package continuation still exist? | `same-package legal = yes / no` | `next-run-eligibility-card.md` |
| were the active slots consumed or severely blocked? | `runs consumed = n / META_ITERATION_COUNT` | `execution-gate-card.md` |
| does another family require a new package? | `fresh ROOT_RUN legal = yes / no` | `package-close-out.md` |
| can the package be launched now? | `launch-ready now = yes / no` | `round-pretraining-brief.md` |

<!-- /table -->

- Do not close the package only because one run was productive.
- Do not start a fresh ROOT_RUN without new pretraining.
- Do not confuse legality with launch readiness.
</details>

| Decision | Means | Owner | Minimum artifact |
| --- | --- | --- | --- |
| `same-package legal` | a heavier same-package continuation still exists | orchestrator | `next-run-eligibility-card.md` |
| `fresh ROOT_RUN legal` | a next package is cold-possible | orchestrator | `package-close-out.md` + handoff cold |
| `launch-ready now` | fresh root has real prepare + pretrain, not only abstract legality | orchestrator | `prepare-proof.md` + `round-pretraining-brief.md` |

<!-- /table -->
</details>

<details>
<summary>2.8 Minimum handoff to reporting — reporting receives only frozen truth</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 220" rolee="img" aria-label="Minimum handoff to reporting">
<defs><marker id="hr" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>
<rect x="35" y="78" width="180" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="125" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">orchestrator truth</text>
<text x="125" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">package identity</text>
<rect x="265" y="78" width="180" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="355" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">counted runs</text>
<text x="355" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">kept / countable</text>
<rect x="495" y="78" width="160" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="575" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">x / target</text>
<text x="575" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">accounting</text>
<rect x="705" y="78" width="180" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="795" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">next-run truth</text>
<text x="795" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">same / fresh</text>
<rect x="935" y="78" width="180" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="1025" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">reporting receives</text>
<text x="1025" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">frozen truth only</text>
<path d="M215 112 H265" stroke="#7aa2ff" stroke-width="2" marker-end="url(#hr)"></path>
<path d="M445 112 H495" stroke="#7aa2ff" stroke-width="2" marker-end="url(#hr)"></path>
<path d="M655 112 H705" stroke="#7aa2ff" stroke-width="2" marker-end="url(#hr)"></path>
<path d="M885 112 H935" stroke="#7aa2ff" stroke-width="2" marker-end="url(#hr)"></path>
</svg>
</div>

<details>
<summary>Legend</summary>

- Reporting does not recalculate and does not rejudge legality.
- Reporting publishes only already frozen runs, accounting, and next-run truth.
- If the handoff cannot be summarized in these four blocks, the orchestrated truth is not closed well yet.
- Before publication, reopen `HTML_EX_LIBRARY_README` and relevant benchmarks from `HTML_EX_LIBRARY_ROOT`.
</details>
</details>
</details>

<details>
<summary>3. Launch legality — before each run</summary>

```text
before run_n
  read agent-runtime.properties
  reopen package memory and the latest decisive artifacts
  identify the baseline already covered by existing tests
  identify new business frontier candidates
  confirm that the previous run fixation is complete when n > 1
  save or update frontier-ranking-ledger.md when candidates compete
  launch exactly one governed run through the adapter
```

- The agent is free inside the slot.
- The orchestrator controles why the slot exists.
- Controles what must remain after the slot.
- Controles whether the next slot is legal.
</details>

<details>
<summary>4. Delegation contract — UI now, API later</summary>

| When | Delegation | What must come back |
| --- | --- | --- |
| run UI | `ui-business-frontier-adapter.html` | business claim, local meaning of the UI proof, code/test delta, UI feedback |
| future API run | `api-flow-discovery-and-atf-test-generation.html` | contract/API claim, payload proof, schema/assertion delta, API feedback |
| artifact fixation | `execute-and-understand-run.html` | diff, proof, XML, MD note, checklist, source carrier; called after wrapper |
| final publication | `reporting.html` | langgraph-business-understanding.html + render + link audit |

<!-- /table -->
</details>

<details>
<summary>5. Stop / continue / fresh-root law — separate truths</summary>

| Verdict | Legal when | Do not use for |
| --- | --- | --- |
| continue same package | a stronger same-round frontier exists, complete fixation, active runs remain | inertia after a good run |
| stop same package | all active runs were consumed or the same-round frontier collapsed cold with a documented severe blocker | fatigue or a report that only looks good |
| fresh ROOT_RUN | a new frontier exists, but exceeds the current package frame | avoiding an unreported blocker |
| repair first | a blocker reduces the validity of any future run | generating tests without proof |

<!-- /table -->

```text
must be published separately
  same-package continuation legal = yes / no
  fresh ROOT_RUN legal = yes / no
  strongest unresolved truth = ...
  materially turned into tests = x / target
  accounting target met = yes / no
  business breadth verdict = broad / partial / narrow
  why the numeric target alone does not raise the score = ...
```
</details>

<details>
<summary>6. Handoff required by the orchestrator — what reporting receives</summary>

```text
the orchestrator hands off to reporting
  link to agent-runtime and interpreted values
  package identity and active accounting
  run list and kept/countable verdicts
  strongest owned business truths
  strongest blocked/partial/fresh-round truths
  roots for the artifact index
  continuation / fresh-root legality
```

- Reporting republishes only frozen truth.
- If fixation is missing: block final reporting.
- Or mark it explicitly as partial.
</details>

function toggleAll(openState){document.querySelectorAll('details').forEach(function(el){el.open=openState;});}
