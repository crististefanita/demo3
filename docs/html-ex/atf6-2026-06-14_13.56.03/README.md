# ROOT_RUN rece pentru authenticated self-service breadth

## Contract de invatare iterativa

1. Problema majora precedenta: pachetul `2026-06-14_13.18.42` a crescut breadth-ul local, dar a ramas in macro-zona `public pre-purchase`, deci nu putea fi vandut rece drept `broad`.
2. Cum o repar acum: ies rece din `public pre-purchase` si atac macro-zona `authenticated self-service`, unde business-ul trece din descoperire publica in proprietate, mesagerie, preferinte si securitate de cont.
3. Ce frontiera aleasa poate produce `1..N` teste bune in acelasi run: fiecare ancora de self-service poate grupa `1..N` probe bune coerente in acelasi run, de exemplu favorites, contact messages sau password change.
4. De ce frontiera aleasa nu este comoda: rupe macro-zona castigatoare anterioara, cere customer lifecycle complet si nu poate fi inchisa rece doar cu suprafata de login sau cu replay de `2FA`.
5. Ce nu repet: nu donez inertial `2FA`, `rentals`, `payment` sau `public pre-purchase`; nu vand login-ul ca frontier; nu umflu targetul cu variante apropiate.
6. Ce semnal auditabil va demonstra ca am invatat: pachetul trebuie sa consume `5 / 5` run-uri, sa lase minimum `5` teste auditabile bune si sa spuna rece daca a iesit sau nu din macro-zona precedenta.
7. Inteleg ca `ROUND_TARGET_TEST_COUNT` este target pe pachet, nu pe run? `yes`
8. Inteleg ca fiecare run trebuie impins spre cat mai multe teste bune daca frontiera permite? `yes`
9. Inteleg minimul obligatoriu de raportare? `yes`

## Recitire live confirmata

- `C:\work\ex\java\demo\common\docs\html-ex\README.md`
- benchmark-uri relevante din `C:\work\ex\java\demo\common\docs\html-ex\`
- `C:\work\ex\java\demo\atf6\test-framework\docs\prompts\iterativ\agent-runtime.properties`
- `C:\work\ex\java\demo\atf6\test-framework\docs\prompts\iterativ\iterative-core-standard.html`
- `C:\work\ex\java\demo\atf6\test-framework\docs\prompts\iterativ\prompt-evolution-orchestration-standard.html`
- `C:\work\ex\java\demo\atf6\test-framework\docs\prompts\iterativ\reporting.html`
- `C:\work\ex\java\demo\atf6\test-framework\docs\prompts\iterativ\ui-flow-discovery-and-atf-test-generation.html`
- `C:\work\ex\java\demo\atf6\test-framework\docs\out\README.md`

## Ce a mers

- pachetul a iesit explicit din macro-zona `public pre-purchase`
- `run_01` a inchis identity continuity prin profil + forgot-password confirmation
- `run_02` a inchis account entry surfaces prin overview, invoices si messages empty guidance
- `ROUND_TARGET_TEST_COUNT = 5` a fost atins exact prin `5` teste auditabile bune
- `META_ITERATION_COUNT = 5` a fost consumat complet si onest
- raportul final a ramas dark, colapsabil, business-first, cu render proof si link audit valide

## Ce a ramas cap

- breadth-ul ramane `partial`, nu `broad`
- favorites ownership nu s-a inchis rece: empty-state a ramas pe `register error surface`
- messages reply progression nu tine rece, desi thread-ul se materializeaza si persista
- password change nu materializeaza schimbarea reala de credential: parola noua nu devine owner, iar parola veche ramane valida

## Ce nu mai trebuie repetat

- nu vinde iesirea din macro-zona ca si cum ar fi si closure adanc
- nu promova drept owned o familie care are in acelasi run o contradictie business grea
- nu te intoarce comod in `public pre-purchase` doar pentru ca acolo exista win-rate mai mare
- nu ascunde faptul ca targetul a fost atins numai prin doua run-uri kept

## Ce frontiera merita rerank data viitoare

- closure rece pe `favorites ownership`
- closure rece pe `message reply progression`
- closure rece pe `password change`
- una dintre aceste trei trebuie aleasa rece la urmatorul pretrain, fara donatie inerțiala si fara intoarcere in macro-zona precedenta

## Semnal auditabil care demonstreaza invatarea

- `tests produced = 5 / 5`
- `runs consumed = 5 / 5`
- `macro-zone exit = yes`
- `breadth verdict = partial`
- diff radacina, diff per run, Extent, XML, command-result, render proof si link audit sunt toate salvate

## Raport canonic si artefacte cheie

- raport final: [analysis/langgraph-business-understanding.html](/C:/work/ex/java/demo/atf6/test-framework/docs/out/2026-06-14_13.56.03/analysis/langgraph-business-understanding.html)
- render proof: [analysis/canonical-render-check.png](/C:/work/ex/java/demo/atf6/test-framework/docs/out/2026-06-14_13.56.03/analysis/canonical-render-check.png)
- link audit: [review/local-link-check.txt](/C:/work/ex/java/demo/atf6/test-framework/docs/out/2026-06-14_13.56.03/review/local-link-check.txt)
- accounting: [review/quality-accounting-verdict.md](/C:/work/ex/java/demo/atf6/test-framework/docs/out/2026-06-14_13.56.03/review/quality-accounting-verdict.md)
- next run truth: [review/next-run-eligibility-card.md](/C:/work/ex/java/demo/atf6/test-framework/docs/out/2026-06-14_13.56.03/review/next-run-eligibility-card.md)
- diff radacina: [review/root-source-thin-no-index.diff](/C:/work/ex/java/demo/atf6/test-framework/docs/out/2026-06-14_13.56.03/review/root-source-thin-no-index.diff)

## Verdict rece fata de benchmark comun

- pachetul este mai bun decat precedentul pe directia macro-zonala, fiindca iese din `public pre-purchase`
- pachetul nu intra inca in `common/html-ex`
- motivul rece: numai doua subfamilii sunt owned, iar cele trei contradictii grele ramase nu permit verdict de breadth closure teacher-grade

## Note reci 1-100

- frontiera business: `92`
- target count: `22`
- artifacte: `91`
- raportare: `90`
- incredere ca pachetul nu este subtire artificial: `89`
- overall calibrat fata de `common/html-ex`: `74`
