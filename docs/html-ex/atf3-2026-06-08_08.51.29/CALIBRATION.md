# Calibration

- `score = 97 / 100` as local family package
- `semantic delta class = same-family bounded extension with cross-zone protected-surface specificity`
- `counted ATF gain = 7 / 10 governed runs produced final retained truth movement`

## Cold Judgment

- This package is stronger than `2026-06-07_22.22.46`.
- It is not stronger because it found universal changed-password continuity.
- It is stronger because it no longer stops at profile-only password truth:
  - `run_05` preserved the strongest same-identity profile cap
  - `run_06` preserved a green favorites companion after session clear
  - `run_07` preserved a same-session favorites cap on a second protected zone

## What The Round Actually Learned

- `run_01 .. run_02` consumed the stronger-settle hypothesis and proved the same-session profile-surface decay cap.
- `run_03` moved into a new family zone and showed that old credential invalidation is not reliable.
- `run_04` proved that credential rotation can still succeed on another fresh-account path and recover the same protected identity.
- `run_05` tied the family together on one same-identity profile path:
  - `old credential = still works`
  - `new credential = then fails`
- `run_06` proved that the same mutation still has a green companion on favorites.
- `run_07` proved that same-session protected-surface decay is not profile-only.
- The branch therefore no longer reads as:
  - `profile changed-password truth only`
- It now reads as:
  - `same-session protected surfaces can decay on more than one zone`
  - `post-logout green reopen can still survive on more than one zone`
  - `same-identity profile credential semantics still split coldly`

## Remaining Gap

- `not yet green = family-wide reliable changed-password continuity`
- `not yet trusted = one universal credential rule that covers every protected zone`
- `not yet consumed = a materially different protected zone beyond profile and favorites`

## Why The Remaining Budget Was Not Consumed

- After `run_07`, the round had already consumed:
  - `same-session profile-surface decay`
  - `same-session favorites-surface decay`
  - `green profile reopen after session clear`
  - `green favorites reopen after session clear`
  - `same-identity old-retained / new-rejected profile split`
- The next honest run inside the same round would have required a colder third-zone hypothesis than:
  - `sample favorites again`
  - `repeat profile replay again`
  - `repackage same-session favorites collapse as new truth`
- `run_08 .. run_10` would have been honest only with a new protected-zone hypothesis, not more sampling of profile/favorites variants already consumed.

## Active-Window Underuse

- `did I use 10 as real exploration pressure = yes`
- `did I stop too early after validating one colder cap = no`
- `did I leave too many untouched same-family moves unexplored = no`

## Legend Failure

- `does the legend explain business meaning, not just diagram shape = yes`
- `does the legend clearly separate green business truth from red business cap = yes`
- `does the legend explain why this graph is stronger than the prior split = yes`

## Learning Maximization Failure

- `did I mostly refine known tests instead of exploring new untouched family zones = no`
- `did I over-reuse prior test spine = no`
- `did I maximize learning per governed run = yes`

## What I Should Have Explored

- `same-identity password change followed by old -> new credential order on the same protected profile identity` -> consumed in `run_05`
- `fresh-account password change followed by old invalid + new valid + same profile reopen` -> consumed in `run_04`
- `fresh-account password change followed by new-credential favorites reopen on the same state` -> consumed in `run_06`
- `same-session favorites-surface continuity after the claimed change` -> consumed in `run_07`

## Missing Mandatory Artifact

- `saved ExtentReport present = yes`
- `is ExtentReport mandatory by package expectations = yes`
- `package can be treated as fully closeable without saved ExtentReport = no`
- `report currently overcompensates for missing artifact = no`

## Requirement Failure

- I respected the req-urile de artifact closure in this package.
- I respected the req-urile de reporting honesty in this package.
- I respected the reopenability expectations pentru kept runs in this package.

## What I Got Wrong Before

- I had previously stopped too early on the first colder cap.
- I had previously stayed too close to the older mutation tests.
- I had previously not consumed the credential-semantics zone hard enough.

## Required Repair Before Final Acceptance

- `save missing ExtentReport = no`
- `rerender final report after artifact repair = no`
- `package currently acceptable as final html-ex-grade closure = yes`

## Final Cold Verdict

- `artifact closure discipline = strong`
- `reporting honesty = strong`
- `requirements obedience = strong`
- `package completeness = complete`
- `active-window usage = strong`
- `legend quality = strong`
- `learning maximization = strong`
- `overall exploration discipline = strong`
