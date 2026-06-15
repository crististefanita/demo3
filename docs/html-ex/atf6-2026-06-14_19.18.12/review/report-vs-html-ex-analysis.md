# Analiza rece a raportului curent vs html-ex

## Scop

Sa judec raportul curent fata de exemplele bune din `C:\work\ex\java\demo\common\docs\html-ex\`, fara sa confund forma buna cu breadth business bun.

## Benchmark-uri recitite

- `C:\work\ex\java\demo\common\docs\html-ex\README.md`
- `C:\work\ex\java\demo\common\docs\html-ex\atf6-2026-06-14_13.56.03\analysis\langgraph-business-understanding.html`
- `C:\work\ex\java\demo\common\docs\html-ex\atf3-2026-06-14_18.01.29\analysis\langgraph-business-understanding.html`
- `C:\work\ex\java\demo\common\docs\html-ex\atf5-2026-06-14_17.57.56\analysis\langgraph-business-understanding.html`
- `C:\work\ex\java\demo\common\docs\html-ex\atf6-2026-06-14_18.09.31\analysis\langgraph-business-understanding.html`

## Tabel de comparatie

| Criteriu | Ce fac bine benchmark-urile | Unde era sub ele raportul local | Ce s-a reparat acum | Nota rece 1-100 |
| --- | --- | --- | --- | --- |
| Deschidere | `13.56.03` deschide calm, clar si sever | raportul local era bun, dar putin prea incarcat in hero si compare-strip | hero-ul a fost rarit, verdictul rece e direct, fara banda comparativa zgomotoasa | 90 |
| Harta aplicatiei | `18.01.29` separa bine `application map` de `package overlay` | localul amesteca inca prea devreme produsul cu pachetul | acum are 3 subgrafuri distincte: produs, overlay, rerank pressure | 92 |
| Noduri business | html-ex bune tin nodurile scurte | unele noduri locale aveau prea multa explicatie compactata | nodurile sunt acum mai scurte, iar explicatia coboara in legende | 91 |
| Legende | exemplele bune folosesc legenda ca suport, nu ca dubla diagrama | legendele locale puteau fi mai pedagogice | fiecare subgraf are legenda colapsata, scrisa ca ajutor de lectura | 92 |
| Colapsare | sectiunile secundare trebuie sa stea inchise la prima citire | localul era aproape bun, dar mai avea zgomot sus | doar `Business Flow Graph` ramane deschis implicit | 94 |
| CSS calm | unele html-ex sunt mai calme vizual decat altele | localul mostenea stiluri acumulate si encoding urat in marcatori | CSS-ul comun a fost simplificat, fara simboluri rupte si cu aliniere stanga constanta | 91 |
| Onestitate | `18.09.31` este puternic pe onestitate cand run-ul cade | localul bun risca sa para prea frumos pentru un standing doar partial | raportul spune rece ca poate depasi exemple pe forma, dar nu si pe breadth | 93 |
| Standing business | benchmark-ul bun rupe macro-zone sau frontiere mai grele | localul sta tot in `registered customer owned journeys` | acest punct nu a fost cosmetizat; verdictul ramane partial | 84 |

## Verdict rece

- Raportul curent poate depasi multe exemple din `html-ex` pe forma, calm, structura si pedagogie a grafului.
- Raportul curent nu trebuie vandut ca peste benchmark pe standing business, fiindca breadth-ul mare de produs este inca partial.
- Cea mai sanatoasa formulare este:
  - `peste benchmark pe raportare si lizibilitate`
  - `inca sub benchmark pe breadth si greutate de frontiera`

## Ce nu trebuie repetat

- sa nu las `hero`-ul sa arate ca un panou promotional
- sa nu las nodurile din graf sa devina mini-paragrafe
- sa nu las raportul sa para mai mare decat pachetul pe care il reprezinta
- sa nu pretind `broad` cat timp macro-zona dominanta ramane aproape aceeasi
