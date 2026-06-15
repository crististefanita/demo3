# run_04

- class: `com.endava.ai.atf.ui.AuthenticatedPostPurchaseHistoryTests`
- claim business: purchase success nu materializeaza istoric de invoice in cont, nici dupa relogin, nici imediat dupa succes
- verdict: `5 / 5`
- mutatie istorica:
  - lane-ul post-purchase nu este inchis frumos; devine contradictie business rece repetata pe contracte de plata diferite
