# Memorie rece ATF1

Question
Care este principiul care guverneaza urmatorul ROOT_RUN?

Answer
Maximizeaza autoinvatarea auditabila pe business nou, real, insuficient acoperit de testele existente.

Nu optimiza pentru:

- un flow deja bine acoperit
- raport frumos
- doar un green simplu

Optimizeaza pentru:

- business nou real
- teste valide si profunde
- Business Flow Graph care explica aplicatia
- artefacte care lasa invatarea redeschisibila rece

Question
Care sunt constantele active nenegociabile?

Answer
- `RUN_EXPECTED_DELTA_QUALITY = 8.50`
- `ROUND_TARGET_TEST_COUNT = 5`
- `META_ITERATION_COUNT = 5`

Citire rece:

- `RUN_EXPECTED_DELTA_QUALITY` = pragul minim al unui run kept
- `ROUND_TARGET_TEST_COUNT` = presiunea de teste bune auditabile pe run
- `META_ITERATION_COUNT` = presiunea de run-uri consumate pe pachet
- cele doua contoare nu se compenseaza si nu se amesteca

Question
Ce trebuie obligatoriu sa lase orice pachet bun?

Answer
Obligatoriu:

- `analysis/langgraph-business-understanding.html`
- Business Flow Graph cu legenda
- `review/root-source-thin-no-index.diff`
- `runs/run_##/review/thin-no-index.diff`
- `ExtentReport_*.html`
- XML + `command-result.md`
- `review/quality-accounting-verdict.md`
- `review/package-close-out.md`
- hyperlink-uri locale catre toate artefactele decisive in raport

Permis si incurajat:

- orice fisier `*.md` util salvat de agentul AI, daca adauga invatare auditabila

Question
Care este adevarul local curent?

Answer
In `docs/out` nu exista acum memorie locala pastrata care sa bata standardele live.

Pornirea urmatoare trebuie facuta rece, de la:

- `agent-runtime.properties`
- HTML-reqs live
- `common/docs/html-ex/README.md`

Question
Ce nu vrem sa se repete?

Answer
- sa fie revendicat drept business nou ceva deja baseline-covered
- sa se aleaga o frontiera confortabila doar pentru ca e usor de inghetat
- sa fie folosit raportul ca substitut pentru lipsa de business nou

Question
Note reci 1-100 acum?

Answer
- `principle clarity` = `90`
- `local memory usefulness` = `68`
- `artifact pressure clarity` = `90`
- `graph pressure clarity` = `85`
- `overall readiness for cold restart` = `84`

Question
Care este adevarul local al pachetului `2026-06-15_11.48.50`?

Answer
- winner rece: `authenticated self-service breadth`
- owned rece:
  - `run_01` = hub de cont si controale de securitate
  - `run_03` = lifecycle complet pe favorites
  - `run_04` = contact -> inbox -> details -> reply controls
- blocked rece:
  - `run_02` = profile update nu arata `Your profile is successfully updated!`
  - `run_05` = reply submit nu reapare in thread
- `tests produced = 16 / 5`
- `runs consumed = 5 / 5`
- `same-package continuation legal = no`
- `fresh ROOT_RUN legal = yes`

Question
Ce trebuie pastrat obligatoriu cand se redeschide acest pachet?

Answer
- `docs/out/2026-06-15_11.48.50/analysis/langgraph-business-understanding.html`
- `docs/out/2026-06-15_11.48.50/review/root-source-thin-no-index.diff`
- `docs/out/2026-06-15_11.48.50/runs/run_##/review/thin-no-index.diff`
- Extent-urile per run
- `quality-accounting-verdict.md`
- `next-run-eligibility-card.md`
- feedback-urile `lesson-learned.md` si `blocker-analysis.md`
