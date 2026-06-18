<!-- Generated from ../html_EN/score-overview-reference.html. Keep source of truth in html_EN. -->
<!-- Source stylesheet: [shared-report-reference.css](../../shared-report-reference.css) -->

# Score Overview Reference `SCORE` `OVERALL` `CAP` `REUSE`

- Explains only the `Score Overview` section from the final report.
- Fixes the canonical shape for axes, weighting, cap, and `Overall`.
- `reporting.html` remains owner for the final page skeleton.



## Overview

| Badge | Read here |
| --- | --- |
| `SCORE` | what the cards published in the opening mean |
| `OVERALL` | why the final score stays where it stays |
| `CAP` | why good reporting does not compensate for a missing business object |
| `REUSE` | canonical shape reused by the report without drift |
| `INVALID` | aesthetic score, locally renamed axes, or long self-defense |

<!-- /table -->

| Category | Scope |
| --- | --- |
| Owner | `Score Overview` `Overall` `cold cap` |
| Uses | `score cards` `canonical axes` `business substance` |
| Does not produce | `report skeleton` `legality run` `artifact production` |

<!-- /table -->

Overall = canonical calculation from axes; it is not set aesthetically in HTML.

<details>
<summary>Minimum mechanical model — closed default</summary>

| Step | Action |
| --- | --- |
| 1 | read the canonical axes |
| 2 | identify the owned business object or its absence |
| 3 | cap `Overall` by substance, not by design |
| 4 | writes in 1-2 lines what keeps the score high and low |

<!-- /table -->
</details>

<details>
<summary>Minimum contract — reference closed by default</summary>

| Element | Rule |
| --- | --- |
| position | sub opening, collapsed and closed default; in final report remains closed, the same as the rest of the main sections |
| cards | only the canonical cards used in the current report |
| single source | if there is drift between report and scoring, this file overrides the local shape |
| overall | explicit and cold, not decoration |
| explanation | why the overall stays here, not just what number it has |
| limit | do not let audit or shape compensate for a missing business object |
| good form | the table model is: `Axis \| Score \| Weight \| Why it sits here` |
| same axes | the visible cards and table must speak about the same axes; do not rename and do not duplicate |

<!-- /table -->
</details>

## 1. Flows and diagrams — how the section is read

<details>
<summary>1.1 Quick reading map — short orientation</summary>

| If you ask | Open | You learn cold |
| --- | --- | --- |
| what must the section explain? | `1.2` | scores and the overall cap |
| which axes are good? | `1.3` | short business-first cards |
| how is the overall read? | `1.4` | cold number, not design prize |
| what breaks it? | `1.5` | over-scoring, duplication, jargon |

<!-- /table -->
</details>

<details open>
<summary>1.2 Minimal state machine — cards -> overview -> limit</summary>

```text
read_cards

-> read_owner_scores

-> publish_overall

-> explain_why_here

-> stop
```

| Step | What you do | What you do not do |
| --- | --- | --- |
| `read_cards` | read the already frozen cards | do not invent new axes to inflate the score |
| `publish_overall` | show the current overall | do not let it look independent from business substance |
| `explain_why_here` | say what keeps it high and what keeps it low | do not write a long self-defense essay |

<!-- /table -->
</details>

<details>
<summary>1.3 Minimum rubric for cards — what deserves to remain visible</summary>

| Axis | What question it answers | What invalidates it |
| --- | --- | --- |
| `Business Substance` | what real business object was owned? | good form without object persistent or closure real |
| `Business Graph Realism` | does the graph sound like the real application? | the graph sounds like a run journal or test list |
| `Historical Reopenability` | can the package be cold-reopened through diffs and artifacts? | history only through prose, without a clear diff spine |
| `Proof Chain Quality` | is claim -> diff -> Extent -> XML / result easy to follow? | the claim floats without concrete proof |
| `New Artifacts Contract` | are state files, the gate card, and render/link proof clean? | new artifacts are missing or only mentioned, not saved |

<!-- /table -->

- `Overall` remains mandatory in canonical table.
- Visible score cards may vary.
- If the opening has a card for `Overall`, do not duplicate it under another name.

- `Business Substance`
- `Business Graph Realism`
- `Historical Reopenability`
- `Proof Chain Quality`
- `New Artifacts Contract`
- `Overall`
</details>

<details>
<summary>1.3.1 Canonical table model — taken as form from the good example</summary>

| Axis | Score | Weight | What it depends on |
| --- | --- | --- | --- |
| Business Substance | .. | 40% | what real business object was owned and what is still missing |
| Business Graph Realism | .. | 20% | how well the graph sounds like the real application, not like a test suite |
| Historical Reopenability | .. | 15% | how well the package can be reopened through diffs and artifacts |
| Proof Chain Quality | .. | 15% | how clearly the claim -> diff -> Extent -> verdict chain is visible |
| Contract Artifacts New | .. | 10% | if state files, gate card, and publication proof are complete |
| Overall | .. | final | final package score, reread through the business object that is missing or was closed |

<!-- /table -->

This is a good form to reuse.
 Do not change the columns and do not replace the column

Why it sits here

with long prose or self-defense.

- Opening cards may vary.
- They summarize the canonical axes.
- No parallel axes appear.
- `Overall` does not disappear from the table.
</details>

### 1.4 How to write Overall — cold number, not prize

<!-- diagram-readable-table -->
| Score block | Function | Guardrail |
| --- | --- | --- |
| Canonical cards | expose separate axes | do not rename axes locally |
| Overall | final number | derived, not decorative |
| Why Overall stays here | explains what lifts and caps the score | business substance caps presentation |
<!-- /table -->

```mermaid
flowchart LR
    Cards["Canonical score cards"] --> Overall["Overall"]
    Overall --> Why["Overall explanation"]
```

Good short formula:

Overall = final package score; it is read cold, not as a prize for form.

| Step | Rule | You publish |
| --- | --- | --- |
| 1 | calculate raw weighted from the canonical axes | `weighted raw = N` |
| 2 | apply the business-substance cap, if it exists | `cap reason` |
| 3 | round only after applying the cap | `Overall = min(round(raw), cap)` |

<!-- /table -->

<details>
<summary>1.5 Typical mistakes — what must be avoided</summary>

| Mistake | Why it is wrong | Good cold form |
| --- | --- | --- |
| high reporting score sold as `Overall` | form does not compensate for the missing business object | overall follows substance, not polish |
| too many cards | dilutes the message and helps self-justification | keep only the canonical axes |
| `Overall cold` or a parallel label | creates drift versus the canonical shape | use only `Overall` |
| long scoring essay | the reader loses the rule and is left with prose | short explanation: what keeps it high, what keeps it low |
| audit jargon in the opening | hides the product | the detail moves down into the legend or proof mapping |

<!-- /table -->
</details>
<details>
<summary>2. Minimum reusable template — what can be copied without drift</summary>

```text
  <details>

  <summary>Score Overview</summary>

  <p>Overall = XX. Good reporting does not compensate for a missing business object.</p>

  <table>

    <thead><tr><th>Axis</th><th>Score</th><th>Weight</th><th>Why it sits here</th></tr></thead>
    <tbody>

      <tr><td>Business Substance</td><td>...</td><td>40%</td><td>...</td></tr>

      <tr><td>Business Graph Realism</td><td>...</td><td>20%</td><td>...</td></tr>

      <tr><td>Historical Reopenability</td><td>...</td><td>15%</td><td>...</td></tr>

      <tr><td>Proof Chain Quality</td><td>...</td><td>15%</td><td>...</td></tr>

      <tr><td>New Artifacts Contract</td><td>...</td><td>10%</td><td>...</td></tr>

      <tr><td>Overall</td><td>...</td><td>final</td><td>...</td></tr>

    </tbody>

  </table>

</details>
```

If it starts sounding like self-defense, it is already too much.
</details>

