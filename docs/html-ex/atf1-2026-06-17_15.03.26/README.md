# ROOT_RUN 2026-06-17_15.03.26

## Identity

- package type: fresh ROOT_RUN
- active branch: develop
- runtime law:
  - RUN_EXPECTED_DELTA_QUALITY = 8.50
  - ROUND_TARGET_TEST_COUNT = 5
  - META_ITERATION_COUNT = 5
- chosen winner: payment success lacks a persistent commercial identity and still cannot bridge into orders, invoices or receipt id
- fallback: relogin still cannot recover a persistent commercial identity

## Execution close

- runs consumed = 5 / 5
- tests executed = 25 / 25
- central truth:
  - authenticated payment success is real
  - the checkout success surface still exposes no order id, receipt id or invoice identity
  - account reentry and relogin still do not reopen a persistent commercial object

## Cold comparison versus html-ex

- this package is sharper than `common/docs/html-ex/atf1-2026-06-17_12.12.36`
- the real gain is not more proof volume, but a better upstream cut:
  - `payment success -> missing commercial identity -> missing orders / invoices after reentry`
- this package is still below `common/docs/html-ex/atf5-2026-06-17_13.09.34`
- the reason is cold and simple:
  - `ATF5 13.09.34` owns a persistent positive object: `message row` + `message detail thread` + reopenability
  - this package still owns a blocker, not a positive post-purchase commercial object

## What reporting improved for real

- the report now starts from application objects, not from test-suite language:
  - `Authenticated checkout -> Payment success -> Receipt / order identity -> My Orders -> Invoices`
- the blocker is placed at the first missing commercial identity, not only at downstream empty pages
- `Historical Reopen Strip` is complete:
  - root diff
  - run_01..run_05 diff
  - Extent per run
  - command-result per run
- `Proof Mapping` is cleaner:
  - each claim points to run diff + Extent + XML
- `Business Flow Graph` is more realistic than older ATF1 forms because it shows product promise first, then package ownership, then the closure gap
- the report now also matches the canonical html-ex visual family much better:
  - same `header` + `hero` + `score-wall` structure
  - same `details.card` section rhythm
  - same `priority-grid`, `legend-card`, `toolbar`
  - same rule that only `Business Flow Graph` stays open by default
  - same node text sizing as the reference graph CSS
- the header is now materially calmer than older ATF1 forms:
  - only the winner strip remains visible
  - opening truth, runtime constants and navigation are collapsed
  - heavy graph interpretation is collapsed under the legend, not pushed into the visible overview

## What cannot be improved only from reporting

- no report rewrite can create:
  - `order row`
  - `invoice row`
  - `receipt id`
  - `order id`
- because of that, this package does not become replacement over the strongest `html-ex` candidates
- it can beat weaker `html-ex` forms on sharpness of wording, graph placement and now also visual discipline
- it cannot beat stronger `html-ex` substance with the same underlying negative business truth

## Overall notes

- Business Substance = `87`
- Business Graph Realism = `89`
- Historical Reopenability = `94`
- Proof Chain Quality = `93`
- Contract Artefacte Noi = `95`
- Overall = `90`

## Top overview: this package plus html-ex

1. `ATF5 html-ex 2026-06-17_13.09.34` -> `92`
2. `ATF5 html-ex 2026-06-17_13.59.19` -> `91`
3. `ATF1 local 2026-06-17_15.03.26` -> `90`
4. `ATF1 html-ex 2026-06-17_12.12.36` -> `90`
5. `ATF1 html-ex 2026-06-17_12.39.34` -> `90`
6. `ATF6 html-ex 2026-06-17_12.05.36` -> `90`
7. `ATF3 html-ex 2026-06-17_12.58.46` -> `90`

## Cold verdict

- package discipline and artefacts are complete
- package reporting is stronger than older ATF1 html-ex on upstream blocker placement and now also on style parity with the current html-ex family
- package substance remains partial
- overall stays `90`
- this package now beats `ATF1 html-ex 12.12.36` more clearly on:
  - calmer title section
  - better collapse discipline
  - more precise placement of the first business rupture
- this package is stronger than a simple `orders missing / invoices empty` replay because it moves the blocker upstream to the first surface that should expose the commercial object
- it still does not become replacement over the strongest `html-ex` candidates because no persistent object materializes positively
