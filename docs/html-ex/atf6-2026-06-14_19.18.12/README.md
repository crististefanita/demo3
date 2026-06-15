# ROOT_RUN rece pentru breadth real pe journey-uri de client autentificat

## Contract de invatare iterativa

1. Problema majora precedenta: pachetul `2026-06-14_18.09.31` avea raport bun si audit bun, dar ascundea in centru un `run_04 = 1 / 5`.
2. Cum o repar acum: aleg un pachet mai aproape de aplicatia reala, cu cinci journey-uri diferite ale clientului autentificat, si nu accept niciun slot sub `5 / 5`.
3. Ce frontiera aleasa poate produce `1..N` teste bune in acelasi run: hub-ul de cont, favorites, mesaje, checkout autenticat si accesul protejat dupa `2FA` sustin natural familii coerente de cate `5` teste bune.
4. De ce frontiera aleasa nu este comoda: nu reambalez acelasi `security pack`; mut presiunea pe business-ul real al aplicatiei dupa autentificare, inclusiv ownership, suport si post-checkout gap.
5. Ce nu repet: nu compensez un run slab cu total mare, nu vand o familie de securitate drept harta aplicatiei si nu las XML-uri amestecate intre sloturi.
6. Ce semnal auditabil va demonstra ca am invatat: toate cele `5` run-uri au `5 / 5`, iar fiecare slot are `command-output`, `command-result`, `Extent`, `XML`, `checklist`, `score-support`, `source-delta` si `lesson`.
7. Inteleg ca `ROUND_TARGET_TEST_COUNT` este targetul activ pe run? `yes`
8. Inteleg ca fiecare run trebuie impins spre cat mai multe teste bune daca frontiera permite? `yes`
9. Inteleg minimul obligatoriu de raportare? `yes`

## Recitire live confirmata

- `C:\work\ex\java\demo\atf0\test-framework\docs\prompts\iterativ\agent-runtime.properties`
- `C:\work\ex\java\demo\atf0\test-framework\docs\prompts\iterativ\iterative-core-standard.html`
- `C:\work\ex\java\demo\atf0\test-framework\docs\prompts\iterativ\prompt-evolution-orchestration-standard.html`
- `C:\work\ex\java\demo\atf0\test-framework\docs\prompts\iterativ\reporting.html`
- `C:\work\ex\java\demo\common\docs\html-ex\README.md`
- `C:\work\ex\java\demo\common\docs\html-ex\atf6-2026-06-14_13.56.03\README.md`
- `C:\work\ex\java\demo\common\docs\html-ex\atf6-2026-06-14_16.44.39` nu exista pe disk; nu l-am inventat
- `C:\work\ex\java\demo\atf6\test-framework\docs\out\README.md`
- ultimul pachet local real: `C:\work\ex\java\demo\atf6\test-framework\docs\out\2026-06-14_18.09.31`

## Verdict pachet

- identitate pachet: `journey-uri de client autentificat`
- `RUN_EXPECTED_DELTA_QUALITY = 8.50`
- `ROUND_TARGET_TEST_COUNT = 5` pe run
- `META_ITERATION_COUNT = 5` pe pachet
- `runs consumed = 5 / 5`
- distributie rece pe run:
  - `run_01 = 5 / 5`
  - `run_02 = 5 / 5`
  - `run_03 = 5 / 5`
  - `run_04 = 5 / 5`
  - `run_05 = 5 / 5`
- `countable good tests total = 25`
- verdict owner-law: `conform`
- breadth verdict: `partial`

## De ce acest pachet este mai bun decat `2026-06-14_18.09.31`

- elimina complet run-ul sever sub target; nu mai exista compensare falsa prin total mare
- muta pachetul din `security pack explained` spre harta reala a aplicatiei: cont, favorites, mesaje, checkout si acces protejat
- graful business descrie journey-uri de produs, nu doar familii de teste sau mecanici `2FA`
- artefactele machine au fost recapturate cu curatare intre run-uri, fara reziduuri XML din sloturile anterioare

## Ce a mers

- toate cele `5` sloturi au atins exact targetul live de `5 / 5`
- pachetul acopera coerent ownership de cont, ownership de favorites, materializare mesaje, checkout autenticat si gating-ul de acces dupa `2FA`
- raportul final ramane minimalist, dark, colapsabil si business-first

## Ce a ramas cap

- breadth-ul ramane `partial`, fiindca pachetul sta in continuare in macro-zona `registered customer owned journeys`
- exista suprapunere usoara intre `hub de cont` si familiile mai adanci de `favorites` si `messages`
- pachetul nu inchide inca o frontiera teacher-grade de tip `order lifecycle`, `password change closure` sau `message reply progression`

## Ce nu mai trebuie repetat gresit

- nu mai amesteca artefacte XML intre sloturi; curatarea rece inainte de fiecare run ramane obligatorie
- nu mai vinde `25` teste totale ca si cum asta ar inlocui judecata pe fiecare run
- nu mai lasa graful sa sune ca jurnal al framework-ului; owner-ul lui ramane aplicatia

## Frontiera care merita rerank data viitoare

- prioritate 1: `password change credential invalidation`
- prioritate 2: `message reply progression` daca poate produce stare business mai grea decat `reply gap`
- prioritate 3: `order lifecycle dupa checkout` dincolo de actualul `invoice gap`

## Artefacte decisive

- raport final: [langgraph-business-understanding.html](analysis/langgraph-business-understanding.html)
- analiza raport vs html-ex: [report-vs-html-ex-analysis.md](review/report-vs-html-ex-analysis.md)
- gate trace: [gate-execution-trace.md](review/gate-execution-trace.md)
- accounting: [quality-accounting-verdict.md](review/quality-accounting-verdict.md)
- next-run truth: [next-run-eligibility-card.md](review/next-run-eligibility-card.md)
- close-out: [package-close-out.md](review/package-close-out.md)
- diff radacina: [root-source-thin-no-index.diff](review/root-source-thin-no-index.diff)

## Recalibrare rece a raportului fata de html-ex

- raportul local poate depasi multe exemple din `html-ex` pe calm vizual, separarea `aplicatie -> overlay pachet -> rerank pressure` si disciplina sectiunilor colapsate
- raportul local nu are voie sa se declare peste benchmark pe standing business, fiindca breadth-ul mare de produs ramane `partial`
- formula corecta este:
  - `raportare peste multe html-ex`
  - `breadth business inca sub benchmarkul principal`

## Feedback agresiv pentru raportul urmator

- Ai avansat mult pe forma, dar inca lasi biblioteca comuna sa intre in raportul final. Asta trebuie oprit.
- Scoate din `analysis/langgraph-business-understanding.html`:
  - `comparatie vs html-ex`
  - `benchmarkul principal`
  - `teacher-grade`
  - orice tabel comparativ cu benchmark-uri
- Pastreaza aceste comparatii doar in:
  - acest `README.md`
  - `review/report-vs-html-ex-analysis.md`
  - `docs/out/README.md`
- Raportul final nu este locul unde explici cat de bun esti fata de exemple. Raportul final trebuie sa ramana doar adevarul aplicatiei si al pachetului local.

## Observatii comparative mutate constructiv din raportul HTML

### Ce trebuie pastrat stabil, chiar daca `html-ex` se schimba

- raportul final trebuie sa fie despre:
  - produsul real
  - overlay-ul pachetului
  - dovezile locale
  - verdictul local
  - miscarea urmatoare
- raportul final nu trebuie sa aiba dependenta de nume de benchmark, fiindca biblioteca comuna este iterativa si se poate rescrie
- comparatia externa traieste mai bine in memorie scrisa, unde poate fi actualizata fara sa polueze artefactul canonic al pachetului

### Ce am invatat concret din comparatia cu biblioteca comuna

- valoarea mare nu este sa numesti exemplul castigator, ci sa extragi regula reutilizabila
- regulile care merita pastrate sunt:
  - deschidere calma, fara panou promotional
  - graph-ul incepe cu aplicatia, nu cu testul
  - noduri scurte, legenda jos, audit separat
  - close-out-ul spune rece `partial` cand breadth-ul e partial
- regulile care nu merita fixate in raport sunt:
  - nume de directoare benchmark
  - scoruri comparative explicite in pagina finala
  - formularea ca un exemplu extern ar fi punct absolut de adevar

### Feedback constructiv pentru urmatorul autor al raportului

- daca simti nevoia sa scrii `mai bun decat X` in raportul HTML, probabil comparatia trebuie mutata aici, in README
- daca un criteriu nu poate supravietui fara numele unui exemplu extern, atunci criteriul inca nu este formulat suficient de bine
- incearca sa reformulezi orice comparatie externa in regula generala de forma:
  - `ce efect vrem in raport`
  - `ce zgomot vrem sa evitam`
  - `ce dovada locala sustine asta`

### Intrebari bune pentru pachetul urmator

- Business Flow Graph-ul spune clar produsul real daca stergi complet orice memorie despre benchmark-uri?
- Daca cititorul vede doar raportul HTML, intelege ce a owned pachetul fara sa aiba nevoie de comparatie externa?
- Daca `html-ex` se rescrie maine, raportul actual ramane complet inteligibil si onest?
