# Round Pretraining Brief

## Surse redeschise

- runtime: `docs/prompts/iterativ/agent-runtime.properties`
- owner-law: `iterative-core-standard.html`, `prompt-evolution-orchestration-standard.html`, `reporting.html`, `ui-flow-discovery-and-atf-test-generation.html`
- memorie locala: `docs/out/README.md`
- reper comun: `common/docs/html-ex/README.md` si benchmark-urile relevante redeschise live
- pachet precedent: `docs/out/2026-06-14_20.16.14`

## Ce iau rece

- deschiderea raportului trebuie sa ramana business-first si colapsata rece
- `run diff` si `root diff` trebuie hyperlink-uite explicit
- gate-ul trebuie sa arate si frontierele respinse sau nepromovate, nu doar winner-ul
- fiecare run trebuie impins pana la `5 / 5` sau inchis brutal ca blocker sever

## Ce NU copiez

- business-ul altui benchmark
- standing-uri, scoruri sau formule comparative in raportul final
- un pachet auth-heavy vandut ca breadth mare
- comfort loop pe `2FA` sau pe suprafete de acces deja inchise

## Ce spun intrebarile si raspunsurile din memoria locala

- targetul numeric nu salveaza singur breadth-ul
- comparatiile externe trebuie sa ramana in README, nu in HTML final
- identitatea pachetului trebuie rescrisa dupa freeze-ul real daca rerank-ul schimba run-urile kept

## De ce acest winner bate rece acum

- iese din pachetul precedent fara sa revina la auth-heavy
- leaga zone reale ale aplicatiei: cont, recovery si checkout autentificat
- pastreaza expuse contradictiile grele `message reply progression` si `password change`

## Ce NU promotionez

- `2FA protected surfaces` nu devine winner automat doar pentru ca o rerulare izolata a iesit verde
- un slot nou de checkout nu se mai justifica in acest pachet dupa `run_05`, fiindca ar repeta aceeasi tensiune centrala

## Cum se inchid contoarele

- `ROUND_TARGET_TEST_COUNT = 5`:
  - fiecare run kept are exact `5` teste auditabile bune
- `META_ITERATION_COUNT = 5`:
  - pachetul consuma exact `5` run-uri kept
  - exploratoriile respinse sau nepromovate raman in gate trace, fara sa primeasca slot kept fals
