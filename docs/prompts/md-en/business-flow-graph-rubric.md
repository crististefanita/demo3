<!-- Generated from ../html_EN/business-flow-graph-rubric.html. Keep source of truth in html_EN. -->
<!-- Source stylesheet: [shared-report-reference.css](../../shared-report-reference.css) -->

# Business Flow Graph Rubric `APP MAP` `OVERLAY` `NODE` `CLOSURE`

- Explains only the drawing and validtion of `Business Flow Graph`.
- `reporting.html` remains the owner of the final page.
- The reusable graph shape lives here.

Open all

Close all

## Overview

| Badge | Read here |
| --- | --- |
| `APP MAP` | the real application: lanes, actors, intents, business objects |
| `OVERLAY` | what the package touched: owned, partial, blocked, fresh-round-only |
| `NODE` | actor -> intent -> business object -> result |
| `LEGEND` | test details, run id, repair, diff, Extent, and procedural jargon |
| `CLOSURE` | the hard object or real rupture left for the next round |
| `INVALID` | the graph becomes a package journal, technical suite, or run list |

<!-- /table -->

Owner:

the real application

business map

Uses:

macro-map

overlay

closure

Does not produce:

final report

proof mapping

score

Do not put in nodes:

run ids

locatori

repair trail

The application is the map. The package is the overlay. If the overlay becomes the map, the graph must be redrawn.

<details>
<summary>Micro-example of a good node</summary>

| Field | Good node | Bad node |
| --- | --- | --- |
| actor | authenticated customer | run_03 |
| intent | reopens post-purchase memory | checks a new helper |
| business object | order row / invoice row | selector / test class |
| result | payment success owned; order history blocked | Extent verde |

<!-- /table -->
</details>

<details>
<summary>Minimum contract — map, overlay, closure</summary>

| Block | Rule |
| --- | --- |
| layer 1 | macro-map application, inclusiv zone relevante neatinse |
| layer 2 | package overlay: what was touched, owned, partial, blocked |
| layer 3 | `next closure pressure`: which hard object or real rupture remains open |
| noduri | short title + at most one short context line |
| mandatory micro-template | `actor -> intent -> business object -> result` |
| legend | collapsed, immediately under the subgraph; move test jargon and procedural detail there |
| default state | in the final report all main sections are collapsible; the slot `4. Business Flow Graph` remains the only one open by default |

<!-- /table -->

| Binary test | Answers |
| --- | --- |
| application map | what exists in the product, including what the package did not touch |
| package overlay | what the package touched, without becoming the main map |

<!-- /table -->
</details>

<details open>
<summary>1. Canonical LangGraph `APP MAP` — macro-map -> overlay -> closure</summary>

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 250" rolee="img" aria-label="Canonical Business Flow Graph">
  <defs><marker id="bgfr" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>
  <rect x="35" y="94" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
  <text x="145" y="120" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">macro-map application</text>
  <text x="145" y="142" text-anchor="middle" fill="#a8b3cf" font-size="11">real product lanes</text>
  <rect x="325" y="94" width="220" height="68" rx="14" fill="#111826" stroke="#35507a"></rect>
  <text x="435" y="120" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">package overlay</text>
  <text x="435" y="142" text-anchor="middle" fill="#a8b3cf" font-size="11">what this package touched</text>
  <rect x="645" y="26" width="210" height="62" rx="14" fill="#111826" stroke="#35507a"></rect>
  <text x="750" y="51" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">owned / countable</text>
  <text x="750" y="71" text-anchor="middle" fill="#a8b3cf" font-size="11">business objects cold-closed</text>
  <rect x="645" y="98" width="210" height="62" rx="14" fill="#111826" stroke="#35507a"></rect>
  <text x="750" y="123" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">partial / blocked</text>
  <text x="750" y="143" text-anchor="middle" fill="#a8b3cf" font-size="11">ruptures, support, limits</text>
  <rect x="645" y="170" width="210" height="62" rx="14" fill="#111826" stroke="#35507a"></rect>
  <text x="750" y="195" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">next closure</text>
  <text x="750" y="215" text-anchor="middle" fill="#a8b3cf" font-size="11">which hard object remains</text>
  <path d="M255 128 H325" stroke="#7aa2ff" stroke-width="2" marker-end="url(#bgfr)"></path>
  <path d="M545 128 C590 112 605 57 645 57" stroke="#7aa2ff" stroke-width="2" fill="none" marker-end="url(#bgfr)"></path>
  <path d="M545 128 H645" stroke="#7aa2ff" stroke-width="2" marker-end="url(#bgfr)"></path>
  <path d="M545 128 C590 144 605 201 645 201" stroke="#7aa2ff" stroke-width="2" fill="none" marker-end="url(#bgfr)"></path>
  </svg>
</div>

Short formula:

macro app map -> package overlay -> next closure pressure.

<details>
<summary>1.1 Reusable SVG micro-template — aligned with html-ex and shared CSS</summary>

- The template is a starting point, not copied business.
- The visual style must remain compatible with `shared-report-reference.css`.
- Shape calibration uses the current examples from `HTML_EX_LIBRARY_ROOT` and `HTML_EX_LIBRARY_README`.

<div class="md-svg-diagram" style="width:100%; overflow-x:auto; margin: 12px 0;">
<svg width="100%" style="max-width:100%; height:auto; display:block;" viewBox="0 0 1160 260" rolee="img" aria-label="Micro-template SVG Business Flow Graph">
      <defs><marker id="mtpl" markerWidth="10" markerHeight="10" refX="8" refY="3" orient="auto"><path d="M0,0 L0,6 L9,3 z" fill="#7aa2ff"></path></marker></defs>
      <rect x="35" y="92" width="190" height="72" rx="14" fill="#111826" stroke="#35507a"></rect>
      <text x="130" y="120" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Actor + intent</text>
      <text x="130" y="143" text-anchor="middle" fill="#a8b3cf" font-size="11">who wants what</text>
      <rect x="300" y="92" width="210" height="72" rx="14" fill="#111826" stroke="#35507a"></rect>
      <text x="405" y="120" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Object business</text>
      <text x="405" y="143" text-anchor="middle" fill="#a8b3cf" font-size="11">real product entity</text>
      <rect x="585" y="34" width="210" height="64" rx="14" fill="#111826" stroke="#20c997"></rect>
      <text x="690" y="60" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Owned</text>
      <text x="690" y="80" text-anchor="middle" fill="#a8b3cf" font-size="11">proven and countable</text>
      <rect x="585" y="124" width="210" height="64" rx="14" fill="#111826" stroke="#f0b429"></rect>
      <text x="690" y="150" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Partial / blocked</text>
      <text x="690" y="170" text-anchor="middle" fill="#a8b3cf" font-size="11">real rupture</text>
      <rect x="875" y="92" width="220" height="72" rx="14" fill="#111826" stroke="#7aa2ff"></rect>
      <text x="985" y="120" text-anchor="middle" fill="#e6edf3" font-size="14" font-weight="700">Next closure</text>
      <text x="985" y="143" text-anchor="middle" fill="#a8b3cf" font-size="11">next hard object</text>
      <path d="M225 128 H300" stroke="#7aa2ff" stroke-width="2" marker-end="url(#mtpl)"></path>
      <path d="M510 128 C540 92 555 66 585 66" stroke="#20c997" stroke-width="2" fill="none" marker-end="url(#mtpl)"></path>
      <path d="M510 128 C540 148 555 156 585 156" stroke="#f0b429" stroke-width="2" fill="none" marker-end="url(#mtpl)"></path>
      <path d="M795 156 C825 156 845 128 875 128" stroke="#7aa2ff" stroke-width="2" fill="none" marker-end="url(#mtpl)"></path>
    </svg>
</div>

| SVG slot | Replace with | Do not put here |
| --- | --- | --- |
| Actor + intent | real actor + product intent | run id, test class, helper |
| Object business | order, invoice, message, profile, cart, comparison etc. | selector, Java method, repair step |
| Owned / Partial / Blocked | the package cold result | score, green Extent, green XML as final result |
| Next closure | the remaining hard object or next rupture | vague plan or "we test more" |

<!-- /table -->
</details>
</details>

<details>
<summary>2. Minimum rubric `NODE` — what to verify before publishing</summary>

| Rubric | Must be visible | Invalidtes |
| --- | --- | --- |
| application map | real product macro-zones | the graph starts directly from runs or technical familys |
| package overlay | what this package changed over the map | the overlay becomes the main map |
| node template | actor -> intent -> business object -> result owned/partial/blocked | the node cannot be read as a real business object |
| business object | actor, intent, object, result | nodes with helper, locator, class name, repair |
| closure pressure | heavy object or remaining real rupture | the graph stops without next truth |
| legend discipline | procedural detail stays below, collapsed | essay inside nodes or a legend that duplicates the whole graph |

<!-- /table -->

| Mandatory micro-template | Question | Good example |
| --- | --- | --- |
| Actor | who has the intent? | `Customer` |
| Intent | what product result do they seek? | `wants post-purchase memory` |
| Object business | what real entity must exist? | `order row / invoice row / message row` |
| Result | what remains after proof? | `Orders shell partial; order row missing` |

<!-- /table -->

- Nodul trebuie citit ca actor -> intent -> business object -> result.
- If it does not work, move the detail into the legend/proof mapping.
- If it still does not work, redraw the node.
</details>

<details>
<summary>3. Good vs bad `NODE` — very short examples</summary>

| Type | Good | Bad |
| --- | --- | --- |
| node name | `Orders history absent after payment success` | `run_03 invoice fix` |
| overlay | `Owned: Messages thread visible` | `Extent ok + xml ok + diff ok` |
| closure | `Next: order row persistent after relogin` | `Mai vedem in run_05` |
| commerce | `Payment success -> order history blocked` | `Checkout tests green` |
| support | `Contact message submitted -> thread/reply ownership partial` | `Support form run_02` |
| account | `Profile setting changed -> persistence after relogin owned` | `Account helper fixed` |

<!-- /table -->
</details>

<details>
<summary>4. Bad smells `INVALID` — when cooling down the graph</summary>

- If the reader remembers `run_03`, `repair`, `reentry` before the business lane, the graph is too procedural.
- If macro-map application incape intr-un colt, iar overlay-ul ocupa tot, report is package-first.
- If the nodes already contain proof, diff, and explanation, the graph is trying to do too much.
- If the legend repeats the whole graph, it is no longer a legend; it is a poorly disguised essay.
</details>

function toggleAll(openState){document.querySelectorAll('details').forEach(function(el){el.open=openState;});}
