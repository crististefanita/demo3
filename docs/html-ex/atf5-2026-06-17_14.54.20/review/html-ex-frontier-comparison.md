# HTML-ex Frontier Comparison

Comparatie facuta live, nu din memorie.

Surse recitite:

- [common html-ex README](../../../../../../common/docs/html-ex/README.md)
- [html-ex ATF5 91 guest checkout](../../../../../../common/docs/html-ex/atf5-2026-06-17_13.59.19/README.md)
- [html-ex ATF6 90 blocker benchmark](../../../../../../common/docs/html-ex/atf6-2026-06-17_12.05.36/README.md)
- [html-ex ATF3 90 authenticated blocker](../../../../../../common/docs/html-ex/atf3-2026-06-17_12.58.46/README.md)
- [local package README](../README.md)
- [local package state](package-state.json)

## Cold ranking

| Package | Overall | Business object truth | Reporting / graph truth | Cold verdict |
|---|---:|---|---|---|
| `html-ex/atf5-2026-06-17_12.08.06` | 92 | persistent `favorite row` | canonic, ownership real | peste noi prin obiect pozitiv persistent |
| `html-ex/atf5-2026-06-17_13.09.34` | 92 | persistent `message row` | canonic, relogin, ownership separation | peste noi prin obiect pozitiv persistent |
| `html-ex/atf5-2026-06-17_13.59.19` | 91 | guest checkout pozitiv pana la payment success | proof chain complet, lane business curat | peste noi pe substanta pozitiva, desi ramane sub order closure |
| `local/2026-06-17_14.54.20` | 90 | deep guest post-payment blocker family | graph application-first, score rece, causal pressure explicit | foarte bun pe intelegere negativa profunda, dar fara obiect comercial nou |
| `html-ex/atf6-2026-06-17_12.05.36` | 90 | deep blocker benchmark | rece si compact, dar mai putin explicit pe lantul cauzal dupa payment success | la acelasi overall; noi suntem mai expliciti pe chain si cap reason |
| `html-ex/atf3-2026-06-17_12.58.46` | 90 | authenticated blocker proof | bun pe `payment + relogin`, comparatie rece separata | la acelasi overall; noi suntem mai buni pe guest checkout chain, el e mai bun pe authenticated reopen path |

## Where the local package is stronger

- fata de `html-ex/atf6-2026-06-17_12.05.36`, pachetul local spune mai clar produsul: `Product -> Cart -> Guest identity -> Billing -> Payment success -> Order identity? -> Invoice / Receipt? -> History?`;
- fata de o parte din exemplele de `90`, `Score Overview` separa mai explicit ce poate ridica scorul fata de ce doar clarifica auditul;
- `Artifact Index` este mai strict pe lantul `claim -> run -> run diff -> root diff -> Extent/XML -> state / gate`;
- `Product Truth Ledger` spune acum si cauzele probabile, dar le marcheaza corect ca ipoteze, nu ca adevar inchis.

## Where the local package is still below the top

- nu depaseste `html-ex/atf5-2026-06-17_13.59.19`, fiindca acela are substanta pozitiva mai mare: guest checkout, billing si payment success intr-un lane business util;
- nu intra in `92` fiindca nu are `favorite row`, `message row`, `invoice row`, `order row`, `receipt identity` sau alta identitate persistenta;
- blockerul este profund si valoros, dar ramane blocker: nu produce closure comercial.

## Cold verdict

Cu artefactele existente, pachetul local `2026-06-17_14.54.20` poate depasi unele exemple `html-ex` la claritate de raportare, la disciplina de calcul a scorului si la realismul grafului aplicatiei.

Nu depaseste liderii reali la `Overall`, pentru ca aceia au obiect pozitiv persistent sau un lane pozitiv mai puternic. Scorul rece ramas legitim este `90`.
