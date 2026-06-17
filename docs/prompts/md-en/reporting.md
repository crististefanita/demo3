<!-- Generated from ../html_EN/reporting.html. Keep source of truth in html_EN. -->
<!-- Source stylesheet: [shared-report-reference.css](../../shared-report-reference.css) -->

# Reporting Standard `OPENING` `GRAPH` `PROOF` `CLOSE-OUT`

- Defines the canonical shape for `analysis/langgraph-business-understanding.html`.
- Publishes the frozen truth: business, proof, accounting, next move.
- Does not reinterpret runtime, launch legality, or production artifacts.

Open all

Close all

## Overview

| Badge | Read here |
| --- | --- |
| `OPENING` | hero, runtime strip, score cards, Score Overview, compact accounting |
| `GRAPH` | real application business map + package overlay |
| `PROOF` | claim -> run -> diff -> Extent/XML -> verdict |
| `AUDIT` | run audit, Artifact Index, state files, render proof, and link audit |
| `CLOSE-OUT` | countability, runs consumed, same-round, fresh ROOT_RUN, next move |
| `INVALID` | pretty report that raises the score above business substance or hides missing proof |

<!-- /table -->

Owner:

final page

published shape

Uses:

frozen truth

proof links

state files

Does not produce:

runtime

launch legality

production accounting

Appendices:

score overview

business graph rubric

Reporting is read-only publication: it takes the closed truth and makes it navigable.

<details>
<summary>Mandatory structure — reference closed by default</summary>

| Zone | Stays visible at the top? | Cold rule |
| --- | --- | --- |
| Visible top | yes | hero verdict, runtime strip, score wall, quick access to `Score Overview` |
| Folded top | only inside fold | Executive Context, extended accounting, jump links, controles, longer explanations |
| Never top | no | run audit, full Artifact Index, long proof tables, detailed close-out, score argumentation |

<!-- /table -->

| Section | What it must contain |
| --- | --- |
| 1. Opening | hero verdict

 runtime strip

 score wall + `Score Overview`

 accounting compact |
| 1a. Score Overview | short collapsed subsection

 the shape comes from `score-overview-reference.html`

 do not reinvent locally |
| 2. Executive verdict | winner truth, strongest unresolved truth, same-round truth, fresh ROOT_RUN truth, and historical mutation versus the previous package |
| 4. Business Flow Graph | macro-map application + package overlay

 the shape comes from `business-flow-graph-rubric.html` |
| 5. Proof and coverage | proof mapping + Artifact Index; each major claim has run, run diff, Extent/XML, verdict MD, and state file |
| 6. Run audit band | a short band per run: claim, delta, kept, diff, Extent/XML, and feedback MD |
| 7. Closure truth | x / ROUND_TARGET_TEST_COUNT

 runs consumed = n / META_ITERATION_COUNT

 target met

 same-round / fresh ROOT_RUN legality

 open problems and under-spend |
| 8. Render and audit | canonical page + shared CSS

render proof + link audit

sections principale collapsible

`4. Business Flow Graph` open default |

<!-- /table -->
</details>

<details open>
<summary>1. Flows and diagrams — form and proof of the final page</summary>

<details>
<summary>1.1 Quick reading map — short orientation</summary>

| If you ask | Open | You learn cold |
| --- | --- | --- |
| what should the first screen look like? | `1.2` | order hero -> runtime -> score cards -> Score Overview -> accounting -> verdict |
| how does reporting read frozen truth? | `1.2.1` | read-only state machine of the final page |
| how should the application, not the package, be drawn? | `1.3` | macro-business map + overlay-ul local |
| how do I link the claim to proof? | `1.4` | lantul claim -> run -> diff -> Extent -> verdict |
| what truths must I not mix? | `1.5` | countability, runs consumed, same-package, fresh-root |

<!-- /table -->

- `1.2` = opening.
- `1.3` = map application.
- `1.4` = proof.
- `1.5` = verdicte separate.
</details>

<details open>
<summary>1.2 Opening shape — first-screen shape</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 220" rolee="img" aria-label="Opening shape">

    <defs><marker id="op" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>

    <rect x="20" y="78" width="160" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="100" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">hero</text>

    <text x="100" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">verdict scurt</text>

    <rect x="200" y="78" width="160" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="280" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">runtime strip</text>

    <text x="280" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">values active</text>

    <rect x="380" y="78" width="160" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="460" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">score cards</text>

    <text x="460" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">visible cards</text>

    <rect x="560" y="78" width="160" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="640" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Score Overview</text>

    <text x="640" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">separate subsection</text>

    <rect x="740" y="78" width="180" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="830" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">accounting compact</text>

    <text x="830" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">integrat, not separat</text>

    <rect x="940" y="78" width="180" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="1030" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">section 2</text>

    <text x="1030" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">Executive verdict</text>

    <path d="M180 112 H200" stroke="#7aa2ff" stroke-width="2" marker-end="url(#op)"></path>

    <path d="M360 112 H380" stroke="#7aa2ff" stroke-width="2" marker-end="url(#op)"></path>

    <path d="M540 112 H560" stroke="#7aa2ff" stroke-width="2" marker-end="url(#op)"></path>

    <path d="M720 112 H740" stroke="#7aa2ff" stroke-width="2" marker-end="url(#op)"></path>

    <path d="M920 112 H940" stroke="#7aa2ff" stroke-width="2" marker-end="url(#op)"></path>

    </svg>
</div>

<details>
<summary>Legend</summary>

- Score cards and `Score Overview` are not the same block; the overview remains a separate subsection.
- Accounting must be integrated after the score area, not as a weak separate helper.
- Jump links are good only if they do not fill the opening with noise.
</details>
</details>

<details>
<summary>1.2.1 Minimum reporting state machine — truth frozen -> final page</summary>

```text
read_package_state

-> read_frozen_truth

-> build_business_graph

-> map_claims_to_proof

-> assemble_run_audit

-> render_and_link_audit

-> publish_final_html
```

| Step | What you read | What you must not do |
| --- | --- | --- |
| `read_package_state` | state files and close-out carriers | not inventezi values new or legality new |
| `build_business_graph` | application macro-map + package overlay | do not turn the graph into a run journal |
| `map_claims_to_proof` | run diff, Extent, XML, MD-uri decisive | do not leave claims without owner and without proof |
| `render_and_link_audit` | CSS common, render proof, link audit | do not publish a beautiful but unauditable page |

<!-- /table -->

- Reporting does not rejudge.
- Read the already frozen state and truth.
- Verify whether the final page is navigable and cold.
</details>

<details>
<summary>1.3 Business Flow Graph — application map + package overlay</summary>

- Here reporting validtes only shape.
- The canonical LangGraph lives in `business-flow-graph-rubric.html`.
- The rubric, examples, and bad smells live there too.

| Loc | Rol |
| --- | --- |
| `1.3` | validtes the graph shape and points to the reusable rubric |
| `4` | describes the mandatory minimum content of the final report |

<!-- /table -->

- `1.3` does not duplicate section `4`.
- Forma se calibreaza in rubrica.
- Published content remains in the report.
</details>

<details>
<summary>1.4 Claim -> proof -> artifact — minimum publication chain</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 220" rolee="img" aria-label="Claim to proof to artifact">

    <defs><marker id="cp" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>

    <rect x="35" y="78" width="180" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="125" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">business claim</text>

    <text x="125" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">what you publish</text>

    <rect x="255" y="78" width="150" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="330" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">run</text>

    <text x="330" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">owner local</text>

    <rect x="445" y="78" width="150" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="520" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">diff</text>

    <text x="520" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">evolution</text>

    <rect x="635" y="78" width="150" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="710" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Extent</text>

    <text x="710" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">proof UI</text>

    <rect x="825" y="78" width="150" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="900" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">verdict MD</text>

    <text x="900" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">owned / blocked</text>

    <path d="M215 112 H255" stroke="#7aa2ff" stroke-width="2" marker-end="url(#cp)"></path>

    <path d="M405 112 H445" stroke="#7aa2ff" stroke-width="2" marker-end="url(#cp)"></path>

    <path d="M595 112 H635" stroke="#7aa2ff" stroke-width="2" marker-end="url(#cp)"></path>

    <path d="M785 112 H825" stroke="#7aa2ff" stroke-width="2" marker-end="url(#cp)"></path>

    </svg>
</div>

<details>
<summary>Legend</summary>

- Every published claim must have an owner and navigable proof.
- If the owner, diff, or proof carrier is missing, the claim is not cold.
- The published claim must sound higher than the test name that supports it.
</details>
</details>

<details>
<summary>1.5 Truths that must not be mixed — countability, runs consumed, same-round, fresh-root</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 220" rolee="img" aria-label="Truths not mixed">

    <rect x="35" y="78" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="145" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">countability</text>

    <text x="145" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">x / ROUND_TARGET_TEST_COUNT</text>

    <rect x="325" y="78" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="435" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">runs consumed</text>

    <text x="435" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">sloturi cheltuite</text>

    <rect x="615" y="78" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="725" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">same-package blocked</text>

    <text x="725" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">not more continui here</text>

    <rect x="905" y="78" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="1015" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">fresh ROOT_RUN legal</text>

    <text x="1015" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">next package</text>

    </svg>
</div>

<details>
<summary>Legend</summary>

- Aceste patru verdicte sunt diferite chiar if apar in same close-out.
- `same-package blocked` does not automatically mean `fresh ROOT_RUN legal = no`.
- If these truths are mixed, the report sounds safer than it is.
</details>
</details>
</details>

<details>
<summary>2. Opening `OPENING` — hero, score wall, Score Overview, accounting compact</summary>

<details>
<summary>2.1 Opening Contract Card — what remains visible and what moves down</summary>

| visible sus | Collapsed / more jos |
| --- | --- |
| hero verdict | long scoring explanations |
| runtime strip | runtime doctrine |
| visible score wall + collapsed Score Overview | self-defense argumentation |
| accounting compact | tabele late de audit |
| short executive verdict | details de test, repair, helper, selector |

<!-- /table -->

- The report publishes; it does not rejudge.
- Every important claim has a local hyperlink to proof.
- The final published shape lives here, not in the execution documents.
- All main sections are collapsible.
- The only main section open by default: `4. Business Flow Graph`.
</details>

<details>
<summary>2.2 Mandatory opening elements — hero + score wall visible; heavy context collapsed</summary>

| area | Model cerut | Cold rule |
| --- | --- | --- |
| Hero | titlu scurt + badge de state | state the package truth, not marketing |
| Score wall | visible cards for the canonical axes, including `Overall` | values follow `score-overview-reference.html` |
| Executive Context | collapsed subsection with runtime pills, short context, owned / blocked | not tine contextul greu open default |
| Read First | mini-card cu root diff or main historical artifact | the first link must help cold reopening |
| Compiled State | mini-card with `package-state.json` | the compiled state is not hidden in Artifact Index |
| Execution Gate | mini-card with `execution-gate-card.md` | package completeness must be quickly verifiable |
| Jump links | short links to Score Overview, Business Flow Graph, Proof, Close-Out | navigatie, not ornament |
| Colapsare | after opening, the main sections are collapsible | in final report, only Business Flow Graph remains open default |

<!-- /table -->

- Good model: `score wall` visible, heavy context in `Executive Context` collapsed.
- Not pune tabele late, audit complet or explicatii lungi in primul ecran.
- Runtime and counters appear as short pills; their doctrine remains in the owner artifacts.
</details>

<details>
<summary>2.3 Accounting Compact — short form published in the opening</summary>

```text
Accounting Compact

  tests produced = x / ROUND_TARGET_TEST_COUNT

  runs consumed = n / META_ITERATION_COUNT

  breadth = broad / partial / narrow

  same-round continuation = yes / no / blocked

  fresh ROOT_RUN = yes / no

  owner = quality-accounting-verdict.md
```
</details>
</details>

<details>
<summary>3. Executive verdict `CLOSE-OUT` — winner, under-spend, next move</summary>

| Block | Minimum content |
| --- | --- |
| Winner truth | what new business became cold, owned, and countable |
| Strongest unresolved truth | which relevant frontier remained partial, blocked, or fresh-round-only |
| Same-round stop truth | why same-package continuation is legal or not |
| Fresh ROOT_RUN truth | whether the next step must move into a new package |
| Historical mutation | in 1-3 lines: what this package changed versus the previous package, with an early link to `root-source-thin-no-index.diff` |

<!-- /table -->

- Report final not devine jurnal lung.
- Still, the opening must have a short historical mutation.
- Without it, diffs remain too low and hard to read pedagogically.

<details>
<summary>3.1 Cold reading discipline — opening shape, good order, cold benchmark</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 220" rolee="img" aria-label="Cold reading discipline">

    <defs><marker id="dr" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>

    <rect x="35" y="78" width="170" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="120" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">opening</text>

    <text x="120" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">hero + cards + accounting</text>

    <rect x="245" y="78" width="170" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="330" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">verdict</text>

    <text x="330" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">winner + under-spend + next move</text>

    <rect x="455" y="78" width="170" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="540" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">graph</text>

    <text x="540" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">povestea business</text>

    <rect x="665" y="78" width="170" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="750" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">proof mapping</text>

    <text x="750" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">claim -&gt; run -&gt; proof</text>

    <rect x="875" y="78" width="110" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="930" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">audit</text>

    <text x="930" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">strip</text>

    <rect x="1025" y="78" width="100" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>

    <text x="1075" y="104" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">close-out</text>

    <text x="1075" y="126" text-anchor="middle" fill="#a8b3cf" font-size="11">truth final</text>

    <path d="M205 112 H245" stroke="#7aa2ff" stroke-width="2" marker-end="url(#dr)"></path>

    <path d="M415 112 H455" stroke="#7aa2ff" stroke-width="2" marker-end="url(#dr)"></path>

    <path d="M625 112 H665" stroke="#7aa2ff" stroke-width="2" marker-end="url(#dr)"></path>

    <path d="M835 112 H875" stroke="#7aa2ff" stroke-width="2" marker-end="url(#dr)"></path>

    <path d="M985 112 H1025" stroke="#7aa2ff" stroke-width="2" marker-end="url(#dr)"></path>

    </svg>
</div>

<details>
<summary>Legend</summary>

- Not lasa opening-ul to devina index tehnic.
- Do not let tests explain the business before the verdict.
- Not ridica payload-ul greu deasupra proof mapping-ului.
</details>

| Rule | Forma cold |
| --- | --- |
| Opening shape | hero -> runtime strip -> canonical score wall

 Score Overview -> integrated accounting -> section 3

 without a separate contradictory block between score and verdict |
| Opening clutter | do not publish accounting, yield, or weak comparator helper as a separate opening block |
| Reading order | the reader must understand the verdict and the business first, not the tests or the repair trail |
| Payload mutat jos | run ids, nume de test, repair steps

 selectori, payload concret, micro-mutatii

 loc: legend, proof mapping, audit strip, artifact index |
| Benchmark cold | for form comparison, reopen the benchmarks selected in pretraining; there is no single rigid teacher |

<!-- /table -->

If the reader must understand the tests first to understand the business winner, the report is wrong.
</details>
</details>

<details>
<summary>4. Business Flow Graph `GRAPH` — the pedagogical center of the report</summary>

- Only the mandatory final-report slot remains here.
- In acest document de cerinte: collapsed default.
- In the final report: the only section open by default.
- Forma and validrea vin din `business-flow-graph-rubric.html`.

| In the report this must be published here | Minim acceptat |
| --- | --- |
| macro-map application | real product lanes, not a run list |
| package overlay | what became owned, partial, blocked, or fresh-round-only |
| legend collapsed | detaliile de test, run ids, metoda and repair trail stau sub graph, not in noduri |
| legatura cu proof | trimite the reader to proof mapping, run audit and Artifact Index |

<!-- /table -->

- Application is map.
- The package colors only what it touched.
- Desenare and validre: `business-flow-graph-rubric.html`.
</details>

<details>
<summary>5. Proof and coverage `PROOF` — mapping, diff, Extent, artifact index</summary>

<details>
<summary>5.1 Ownership = truth closed — claim -> run -> proof</summary>

| Coloana | What must exist |
| --- | --- |
| Business claim | claim cold despre un pas or o identitate business, not only despre un test verde |
| Run owner | link to `runs/run_##/SUMMARY.md` |
| Evolution truth | link to `runs/run_##/review/thin-no-index.diff` for run claim; `root-source-thin-no-index.diff` only for package synthesis |
| UI proof | link to `ExtentReport_*.html` for UI packages |
| Machine proof | link to XML or `command-result.md` |
| Cold verdict | owned / partial / blocked / fresh-round-only |

<!-- /table -->

```text
run diff = small mutation

root diff = compiled mutation

carrier = support only if the diff is not enough
```

- Every important claim has a link to diff.
- Every claim also has a historical mutation sentence.
- Say: what s-a open, closed or ingustat.

- Without run diff, the claim has no complete cold history.
- The root diff does not compensate for the missing small diff.
</details>

<details>
<summary>5.2 Artifact Index = where you reopen proof — what must be hyperlinked</summary>

- Formula: `claim major -> run -> run diff -> Extent/XML -> verdict MD -> state file -> status`.
- One row per major claim published in Verdict, Business Flow Graph, or Proof Mapping.
- Inventarul brut sta in frozen inventory.

| family | Mandatory artifact | Rol |
| --- | --- | --- |
| Runtime | `agent-runtime.properties` | common truth of thresholds and paths |
| Evolutie | `runs/run_##/review/thin-no-index.diff` + `review/root-source-thin-no-index.diff` | run diff for local mutation; root diff for package compilation |
| Proof UI | `ExtentReport_*.html` | coloana principala de proof for UI |
| Proof machine | `testng-results.xml`, `TEST-*.xml`, `command-result.md` | machine-readable execution truth |
| Audit pe run | `SUMMARY.md`, `artifact-checklist.md`, `score-support.md` | truth kept/countable |
| Guvernanta | `quality-accounting-verdict.md`, `next-run-eligibility-card.md`, `package-close-out.md`, `package-state.json`, `execution-gate-card.md` | accounting, legality, close-out, and compiled state |
| State files pe run | `runs/run_##/review/run-state.json` | links every major claim to the cold run state that produced it |
| Constructive learning | any `*.md` that influenced the verdict | feedback auditabil de la agent |
| Publicare | `shared-report-reference.css`, `canonical-render-check.png`, `local-link-check.txt` | render proof and link reopenability proof |

<!-- /table -->

Artifact Index is mandatory per major claim; extra value does not replace it.

| Claim major | Run | Run diff / source | Extent / XML | Verdict MD | State file | Status |
| --- | --- | --- | --- | --- | --- | --- |
| claim published in verdict, graph, or proof mapping | `run_##` or `package` | link to diff/source or `missing` | link to Extent/XML or `n/a` | link to the MD that owns the verdict | link to `run-state.json` or `package-state.json` | `linked / missing / n/a` |

<!-- /table -->

- Artifact Index is not decorative inventory.
- Claim without a complete chain remains partial.

- claim -> run diff
- sinteza -> root diff
- carrier -> only when the claim requires additional interpretation

- State files apar here and in Close-Out.
- `package-state.json` = truth compilat.
- `run-state.json` = claim greu dependent de un slot.
</details>

<details>
<summary>5.3 Allowed extra value — freedom after the mandatory skeleton</summary>

| You may add | When it is worth it | Conditie cold |
| --- | --- | --- |
| subgrafuri business suplimentare | when un singur graph mare devine greu de citit | short collapsed legend under each subgraph |
| mini blocker matrix | when blockers must be separated more clearly | each row has a link to proof |
| comparator local scurt | when un comparator real clarifica standing-ul | to not devina vanity strip |
| learning notes per frontier | when feedback-ul saved schimba real next run | numai cu md local hyperlink-uit |
| specialized proof subsections | when un flow cere explicatii separate for diff, Extent, XML or render | not dubla Artifact Index-ul |

<!-- /table -->

- The mandatory skeleton comes first.
- After it, the agent may add useful subsections.
- Conditie: to mareasca claritatea de Reopenre.

- missing `package-state.json` = report partial.
- missing promised run-state files = partial report.
- Beautiful HTML does not compensate for missing state files.
</details>
</details>

<details>
<summary>6. Run audit band `AUDIT` — one cold band per run</summary>

| Coloana | Minimum content |
| --- | --- |
| Run | `run_##` + link to `SUMMARY.md` |
| Business claim | what it tried to defend and why that run was worth consuming |
| Expected delta | what fel de noutate or adancime se astepta |
| Actual delta | what materialized cold and what historical mutation it added versus the previous state |
| Kept? | yes / no |
| Why countable / not countable | motiv cold, scurt |
| Diff | link to the relevant small run diff; not only to root diff |
| Extent | link for UI or `n/a` explicit |
| Feedback | link to feedback-ul constructiv saved |

<!-- /table -->

- Only root diff = partial history.
- Run audit must lead to the small deltas per run.

- Order good: claim -> run -> run diff -> Extent/XML -> verdict.
- Another order slows historical reading.

- Run with changed source requires a link to `runs/run_##/review/thin-no-index.diff`.
- Without a link, the row is publicly incomplete.

- What did the run prove?
- What changed in package history?
</details>

<details>
<summary>7. Closure truth `CLOSE-OUT` — countability, legality, next move</summary>

<details>
<summary>7.1 Separate publication of closure truths — separates countability, runs consumed, and next move</summary>

| Trebuie separat | What publici |
| --- | --- |
| Countability | `materially turned into tests = x / ROUND_TARGET_TEST_COUNT` |
| Runs consumed | `runs consumed = n / META_ITERATION_COUNT` |
| Accounting target | `accounting target met = yes / no` |
| Business breadth verdict | `broad / partial / narrow` + motiv cold |
| Same-round continuation | `same-round continuation legal = yes / no` |
| Fresh ROOT_RUN legality | `fresh ROOT_RUN legal = yes / no` |
| Open problems | the hardest remaining truths and any unresolved under-spend on tests or runs |

<!-- /table -->

- Do not mix into a single sentence:
- countability, breadth, runs consumed, yield, application exhaustion.
- Counters remain separate.

- Report publica accounting and close-out deja decis.
- Do not move target-production theory here.
- Arata clar what a iesit and verdict final.

- Close-Out shows `package-state.json`.
- Close-Out shows `execution-gate-card.md`.
- If the verdict depends on a run, show `run-state.json` too.
</details>
</details>

<details>
<summary>8. Render and audit `AUDIT` — style, screenshot, hyperlinks</summary>

| Check | Must be published |
| --- | --- |
| Canonical page exists | `analysis/langgraph-business-understanding.html` |
| Shared CSS used | link local la `shared-report-reference.css` |
| Render proof | `canonical-render-check.png` |
| Link audit | `local-link-check.txt` |
| Collapsible discipline | sections mari collapsible, legende scurte subgraphe |

<!-- /table -->

- `canonical-render-check.png` must show the real report.
- `not found`, gray screen, cookie wall, or foreign page = invalid.
- Report cade cold chiar if HTML-ul and linkurile exists.

- Without valid render proof: `New Artifacts Contract` remains at most partial.
- The publishing blocker must be linked explicitly.
</details>

function toggleAll(openState){document.querySelectorAll('details').forEach(function(el){el.open=openState;});}
