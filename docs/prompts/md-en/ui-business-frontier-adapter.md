<!-- Generated from ../html_EN/ui-business-frontier-adapter.html. Keep source of truth in html_EN. -->
<!-- Source stylesheet: [shared-report-reference.css](../../shared-report-reference.css) -->

# UI Business Frontier Adapter `UI` `FRONTIER` `PROOF` `HANDOFF`

- UI adapter for a single run.
- Starts from `SEED_RECORDING_CLASS`.
- Chooses a visible business frontier.
- Produces the test/support slice.
- Hands cold proof to execution.

Open all

Close all

## Overview

| Badge | Read here |
| --- | --- |
| `UI` | what is visible on the page: route, form, message, state, screenshot, anchor |
| `FRONTIER` | new uncovered business: actor, intent, visible step, result |
| `DELTA` | new business truth; judged before UI proof |
| `HANDOFF` | material handed to `execute-and-understand-run.html` |
| `WRAPPER` | UI specialization over canonical run execution |

<!-- /table -->

Owner:

slot UI frontier

actor

intent

visible proof

Uses:

UI runtime

seed

audited pretraining

Delegates:

slot material to execute-and-understand-run

Produces:

UI material

local claim

proof anchors

Does not produce:

package legality

fresh ROOT_RUN

final report

<details>
<summary>UI runtime reference `RUNTIME` — UI bindings read live</summary>

| Binding | Rol | Anti-drift |
| --- | --- | --- |
| `SEED_RECORDING_CLASS` | visible entry point | does not become suite design |
| `TARGET_TEST_PACKAGE` | UI test destination | do not scatter without a cold reason |
| `UI_TEST_ROOT` | test class root | do not leave the declared surface |
| `UI_SUPPORT_ROOT` | UI support root | do not hide business in helpers |

<!-- /table -->

- The UI adapter reads runtime live.
- Active values become the law of the current slot.
- General run artifacts remain in `execute-and-understand-run.html`.
</details>

<details>
<summary>1. Adapter boundary `WRAPPER` — one UI run, not the package</summary>

| Owns here | Forwards | Does not own here |
| --- | --- | --- |
| UI seed
visible frontier
test/support delta
UI proof
local feedback | UI claim
visible anchors
blockers
local lessons
meaning of the UI proof | package legality
run consumption
final close-out
final page |

<!-- /table -->

- If you talk about what is visible in the UI, you are in the adapter.
- If you talk about what is saved, counted, or fixed, you are in `execute-and-understand-run.html`.

<details>
<summary>1.1 Minimum contract</summary>

- The seed is the entry point, not the verdict.
- The frontier is business-first: actor, intent, visible step, result.
- The run produces auditable tests for the chosen anchor hands off a severe blocker.
- Without result business visible and UI proof explainable, flow-ul remains investigation.
- Minimum handoff: UI claim, visible anchors, local result, blocker, and lesson.
</details>

- Strict document for the visible UI frontier.
</details>

<details open>
<summary>2. Flows and diagrams — single UI run band</summary>

<details>
<summary>2.1 Quick reading map — short orientation</summary>

| Reading | Open | You learn |
| --- | --- | --- |
| band | `2.2` | UI wrapper over canonical run execution |
| clasificare | `2.3` | owned / partial / blocked |
| handoff | `2.4` | UI claim, anchors, verdict local, blocker, lesson |

<!-- /table -->
</details>

<details open>
<summary>2.2 UI lane over execute-and-understand-run `WRAPPER` — UI specialization, canonical fixation</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 300" rolee="img" aria-label="UI band over execute-and-understand-run">
<defs><marker id="ui" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>
<rect x="30" y="110" width="150" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="105" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Seed</text><text x="105" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">primul indiciu visible</text>
<rect x="230" y="110" width="165" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="312" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Descopera UI</text><text x="312" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">rute / forms / link-uri</text>
<rect x="445" y="110" width="165" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="527" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Choose frontier</text><text x="527" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">new business</text>
<rect x="660" y="110" width="165" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="742" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Implement the slice</text><text x="742" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">small but relevant delta</text>
<rect x="875" y="110" width="220" height="72" rx="14" fill="#111826" stroke="#35507a"></rect><text x="985" y="138" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Handoff to execute</text><text x="985" y="160" text-anchor="middle" fill="#a8b3cf" font-size="11">anchors + lessons</text>
<path d="M180 146 H230" stroke="#7aa2ff" stroke-width="2" marker-end="url(#ui)"></path><path d="M395 146 H445" stroke="#7aa2ff" stroke-width="2" marker-end="url(#ui)"></path><path d="M610 146 H660" stroke="#7aa2ff" stroke-width="2" marker-end="url(#ui)"></path><path d="M825 146 H875" stroke="#7aa2ff" stroke-width="2" marker-end="url(#ui)"></path>
</svg>
</div>

<details>
<summary>What the UI adapter adds vs what execute manages</summary>

| UI adapter adds | `execute-and-understand-run.html` gestioneaza |
| --- | --- |
| actor, intent, visible step, UI state | canonical run fixation |
| the meaning of screenshots and visible anchors | reopenable proof |
| owned / partial / blocked local | the cold verdict and package memory |

<!-- /table -->
</details>

<details>
<summary>How to read the strip</summary>

- `Seed` is only the entry point, not the verdict.
- `Descopera UI` means visible routes, states, and intents, not recorder replay.
- `Choose frontier` means the most valuable locally visible business mutation.
- `Implement the slice` trebuie to lase o delta mica, dar countable.
- `Handoff to execute` leaves UI material for canonical fixation.
</details>
</details>

<details>
<summary>2.2.1 Minimum UI adapter state machine — seed -> proof -> execute handoff</summary>

```text
read_seed
-> discover_business_object
-> choose_visible_frontier
-> implement_slice
-> execute_and_capture
-> handoff_to_execute_and_understand_run
```

| Step | Minimum output |
| --- | --- |
| `discover_business_object` | actor, intent, and business object, not just page |
| `choose_visible_frontier` | local claim cu result visible posibil |
| `execute_and_capture` | visible anchors and the meaning of UI proof |
| `handoff_to_execute_and_understand_run` | UI material for canonical fixation |

<!-- /table -->

- Adaptorul UI not fixeaza singur package.
- Hands off materia cold a unui singur slot.
- `execute-and-understand-run.html` closes the verifiable run.
</details>

<details>
<summary>2.3 Frontier UI: owned / partial / blocked — UI-adapter-specific reading</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 220" rolee="img" aria-label="Owned partial blocked UI">
<defs><marker id="upb" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>
<rect x="35" y="78" width="240" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="155" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">visible flow</text>
<text x="155" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">route / form / state</text>
<rect x="345" y="30" width="230" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="460" y="56" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">owned</text>
<text x="460" y="78" text-anchor="middle" fill="#a8b3cf" font-size="11">result stabil, visible</text>
<rect x="345" y="94" width="230" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="460" y="120" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">partial</text>
<text x="460" y="142" text-anchor="middle" fill="#a8b3cf" font-size="11">useful support, no new business</text>
<rect x="345" y="158" width="230" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="460" y="184" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">blocked</text>
<text x="460" y="206" text-anchor="middle" fill="#a8b3cf" font-size="11">unstable or insufficient proof</text>
<path d="M275 112 H345" stroke="#7aa2ff" stroke-width="2" marker-end="url(#upb)"></path>
</svg>
</div>

<details>
<summary>Legend</summary>

- `owned` requires stable and explainable visible UI result.
- `partial` may repair support without gaining new business identity.
- `blocked` means the UI does not cold-close the frontier, not merely that the test was inconvenient.
</details>

| Frontier quality | Trebuie to raspunda | If the answer is weak |
| --- | --- | --- |
| expected business delta | what new truth about the application changes the business map, not just visible proof? | respinge helper-only
respinge selector-only
reject shell reachability without a new business object |
| materialized object / blocker | which object, property, history, ownership, or severe blocker becomes verifiable? | reject the winner if it remains only a visible page or successful click |
| expected UI proof | which visible anchors and screenshots can support the claim? | reject the winner if the meaning of UI proof cannot be explained |
| helper / locator risk | does business change, or only technical support? | mark `blocked if only locator-helper work` |

<!-- /table -->

- Business delta is judged before UI proof.
- A weak frontier stops here.
- Without a materializable business object: not a winner.
- Without UI proof explainable: not e winner.
</details>

<details>
<summary>2.4 What the UI adapter hands off `HANDOFF` — handoff to execute-and-understand-run</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 220" rolee="img" aria-label="UI handoff">
<defs><marker id="uho" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>
<rect x="35" y="78" width="180" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="125" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">business claim</text>
<text x="125" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">what it tried</text>
<rect x="255" y="78" width="170" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="340" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">anchors UI</text>
<text x="340" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">what se vede</text>
<rect x="465" y="78" width="160" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="545" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">verdict local</text>
<text x="545" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">owned / partial / blocked</text>
<rect x="665" y="78" width="170" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="750" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">blocker / lesson</text>
<text x="750" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">what the run learns</text>
<rect x="875" y="78" width="230" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
<text x="990" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">feedback MD -&gt; execute</text>
<text x="990" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">material for fixation</text>
<path d="M215 112 H255" stroke="#7aa2ff" stroke-width="2" marker-end="url(#uho)"></path>
<path d="M425 112 H465" stroke="#7aa2ff" stroke-width="2" marker-end="url(#uho)"></path>
<path d="M625 112 H665" stroke="#7aa2ff" stroke-width="2" marker-end="url(#uho)"></path>
<path d="M835 112 H875" stroke="#7aa2ff" stroke-width="2" marker-end="url(#uho)"></path>
</svg>
</div>

<details>
<summary>Legend</summary>

- `3.4` says what exits the run.
- `5` say how se valideaza proof iesirilor.
- Adaptorul UI hands off materia cold: claim, anchors, verdict local, blocker and lesson.
- `execute-and-understand-run.html` decides canonical fixation.
- `orchestrate-iterative-runs.html` decide legality.
- `reporting.html` publica final page.
</details>
</details>
</details>

<details>
<summary>3. Freedom model — heuristics, not a corset</summary>

| Lasa liber agentul pe | Mentine strict pe |
| --- | --- |
| UI exploration order
strategia de locator
note auxiliare
repair local
first attempted branch | new business
visible anchors
verdict local
owner boundary
sensul dovezii UI |

<!-- /table -->

We do not impose rigid micro-steps. But tactical freedom does not reduce the target, proof, or owner boundary.
</details>

<details>
<summary>4. Rapid reject gate `FRONTIER` — stops procedural work before winner selection</summary>

| Respinge cold if | De what | What ceri in schimb |
| --- | --- | --- |
| helper-only | creste suportul, not intelawrea business | actor + intent + result visible |
| shell-only | the page exists, but the business object is missing | row, id, status, coldipt, message or another verifiable entity |
| same absence replay | reconfirms the same absence without new delta | un unghi nou: actor, state, persistenta, recovery or efect downstream |
| no countable business delta | does not change the package verdict | claim countable cu business object or blocker sever documentat |

<!-- /table -->

- This gate applies after exploration freedom and before implementation.
- If it fails here, do not save the run as the frontier winner.
</details>

<details>
<summary>5. Choosing the UI business frontier — the seed is not the business model</summary>

- Map generica: `baseline -> frontier -> owned / partial / blocked / fresh-round-only`.
- Owner: `orchestrate-iterative-runs.html`.
- Here remains only UI-adapter-specific reading.

| UI question | Desired answer | Handoff UI |
| --- | --- | --- |
| What real flow does the seed expose? | un flow de business, not un replay de recorder | why the seed helps or misleads |
| Which UI actor and intent are worth attacking? | un journey visible, not only o componenta or un locator | motivul for which frontier wins locally |
| What visible step changes business truth? | route, form submit, toggle, compare tray, protected state, or another visible mutation | anchorsle visiblee and sensul lor business |
| What poate deveni owned / countable? | numai branch-ul cu result visible stabil and UI proof explainable | verdict local: owned / partial / blocked |
| What remains blocked or fresh-round-only? | published cold, without inventing closure | `ui-blocker-note.md` or next-run truth from the owner documents |

<!-- /table -->

Business object first, selector second. Hand off actor, intent, visible step, result, and the decisive local lesson.

- The final graph shape belongs to reporting.
- Rubric is `business-flow-graph-rubric.html`.
- Here se hands off only materia cold a run-ului UI.
</details>

<details>
<summary>6. Meaning of UI proof `PROOF` — what the adapter explains, not what it fixes</summary>

- `3.4` hands off sensul UI al dovezii.
- `execute-and-understand-run.html` manages canonical fixation.

| UI question | Mandatory answer |
| --- | --- |
| What is visible? | visible step, state, message, row, card, form, or local result |
| De what conteaza? | what truth business schimba or blocheaza |
| Who is tinta? | the exact actor and UI object, without ambiguity |
| What remains partial? | visible limit, blocker, or missing object |

<!-- /table -->

For UI, proof without the explanation of visible anchors remains incomplete.
</details>

<details>
<summary>7. Feedback UI for next agent `HANDOFF` — lessons, not a file inventory</summary>

| Categorie | When apare | What the next agent must learn |
| --- | --- | --- |
| frontier map | when sunt more multe rute/link-uri/branch-uri | covered / blocked / duplicate / deferred / still-open |
| proof meaning | for un run UI proof-bearing | anchors, forms, overlay-uri and visible states |
| target identity | when repeated lists, cards, or rows exist | how se distinge tinta exacta |
| blocker | when proof cannot be closed | missing input, popup, auth, seeded data, or external boundary |
| mechanism learning | when s-a descoperit o interaction reutilizabila | pattern de locator/wait/interaction, without a sell this ca new business |

<!-- /table -->
</details>

<details>
<summary>8. Handoff to execute-and-understand-run `HANDOFF` — what the UI wrapper returns</summary>

```text
the UI adapter returns
  business claim
  actor + intent + visible step + local result
  frontier quality: expected business delta / UI proof meaning / helper risk
  owned / partial / blocked classification
  visible anchors and the meaning of screenshots
  blocker or UI limit, if present
  decisive UI lessons
  kept/countable recommendation for execute-and-understand-run
```

- `execute-and-understand-run.html` fixes package memory.
- `orchestrate-iterative-runs.html` decide legality nextui run.
- Reporting publishes the final map.
</details>

<details>
<summary>9. Common support references `RUNTIME` — runtime, architecture, benchmarks</summary>

| Sursa | Rol cold | What must not be done |
| --- | --- | --- |
| `agent-runtime.properties` | thresholds, paths, and common bindings | not redefini ad-hoc values or radacinile in run |
| `Living_Architecture_UI_API_doc_v1_0.html` | framework reference for UI, configured targets, layers, and technical execution rules | do not turn the API side into this document's object and do not use it as owner of iterative legality |
| benchmark-urile selectate in pretraining | style, graph, history, and audit references already selected in the audited intake | do not inherit their scores, standing, or business |

<!-- /table -->

- Sources are reopened for calibration and severity.
- Adaptorul UI uses contextul lor.
- More departe hands off only materia cold a run-ului curent.
</details>

function toggleAll(openState){document.querySelectorAll('details').forEach(function(el){el.open=openState;});}
