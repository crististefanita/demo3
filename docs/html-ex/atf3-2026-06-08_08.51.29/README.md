# 2026-06-08_08.51.29

- `family = password mutation reliability across same-session protected-surface stability and post-logout credential transition under META_ITERATION_COUNT = 10`
- `winner = run_05`
- `verdict = the branch no longer stops at profile-only truth; after the claimed change, the family now spans profile and favorites too: post-logout green reopen still exists on both protected zones, while same-session protected surfaces still fail coldly and the same-identity profile split remains the strongest retained cap`
- `active governed window = run_01 .. run_10`
- `kept runs consumed = run_01 .. run_07`
- `unused governed budget = run_08 .. run_10, earned only after the round consumed same-session decay on two protected zones plus post-logout green reopen on two protected zones, with no colder third-zone hypothesis left inside the same family`

## Open First

1. `analysis/langgraph-business-understanding.html`
   - canonical package reading
   - final business understanding and active-window accounting
2. `runs/run_05/SUMMARY.md`
   - strongest same-identity cap
   - old credential still reopens the profile surface before the new credential fails
3. `runs/run_06/SUMMARY.md`
   - strongest second-zone green companion
   - new credential reopens the same favorites state after session clear
4. `runs/run_07/SUMMARY.md`
   - strongest second-zone same-session cap
   - favorites does not reopen after the claimed change in the same session
5. `runs/run_04/SUMMARY.md`
   - first green profile companion
   - old credential is rejected, new credential reopens the same protected profile
6. `CALIBRATION.md`
   - cold reason why this package beat the earlier split package

## Cold Position

- `what moved = the branch now owns two protected business zones on the same family`
- `zone A = same-session protected-surface decay`
  - `profile route shell can survive while the protected profile surface is not defended`
  - `favorites route request no longer reopens the favorites surface after the claimed change`
- `zone B = post-logout protected continuity`
  - `green profile companion = old invalid + new valid + same profile reopened`
  - `green favorites companion = new credential reopens the same favorites state after session clear`
  - `winner cap path = old credential still reopens the same protected profile identity before the new credential fails`
- `what stayed green = AccountRouteContinuityTests still reopens the protected route before mutation`
- `what stayed capped = family-wide reliable changed-password continuity and one universal credential rule across every protected zone`
- `why this matters = this is stronger than the earlier package because it no longer publishes only a profile split; it now shows how the same mutation behaves across two real protected zones of the application`

## Active-Window Accounting

- `META_ITERATION_COUNT = 10`
- `active governed runs counted = run_01 .. run_07`
- `run_08 .. run_10 = unused by earned stop, not by decorative accounting`
- `outside-window memory = 2026-06-07_22.22.46 comparative only / no active score credit`

## Why Stop At run_07

- `run_01 .. run_02` consumed the stronger-settle hypothesis and proved the same-session profile-surface cap
- `run_03` moved into a new same-family zone and proved that old credential retention can survive the claimed change
- `run_04` proved the opposite green companion path on another fresh-account route: old invalid, new valid, same profile reopened
- `run_05` tied the split together on one same-identity path:
  - `old credential = still opens the protected profile`
  - `new credential = then fails on fresh login`
- `run_06` opened a second protected business zone and proved the new credential can still recover the same favorites state after session clear`
- `run_07` proved that same-session decay also hits favorites, so the cold surface cap is not profile-only
- after `run_07`, the next honest move would have required a colder third-zone hypothesis than:
  - `sample favorites replay again`
  - `repeat the same profile split`
  - `relabel a same-session favorites collapse as new truth`

## Read This Package As

- `not a universal changed-password continuity win`
- `not a pivot to an easier branch`
- `a hard-frontier round that used 7/10 governed runs to climb from profile-only decay into cross-zone password-mutation truth on profile and favorites`
