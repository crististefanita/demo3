# HTML-EX Benchmark Comparison

Scop:

- comparatie rece intre pachetul local `ATF6 ROOT_RUN 2026-06-17_14.54.17` si exemplele active din `C:\work\ex\java\demo\common\docs\html-ex`;
- fara rerulare;
- fara schimbarea verdictului business al pachetului local;
- cu accent suplimentar pe `maximizarea intelegerii business` prin teste valide, business graph realist si raportare redeschisibila.

## Pachetul local curent

- package: `ATF6 ROOT_RUN 2026-06-17_14.54.17`
- overall publicat: `90`
- rol rece: `deep post-purchase blocker benchmark, not positive ownership benchmark`
- truth central:
  - `checkout success`, `account reopen`, `invoice surface reopen`, `relogin durability` si `cross-customer boundary` exista;
  - totusi nu se materializeaza nici `order row`, nici `invoice row`, nici `receipt identity / order id`.

## Benchmark-uri citite live

### 1. `atf5-2026-06-17_12.08.06`

- overall: `92`
- de ce sta sus:
  - are obiect persistent pozitiv real: `favorite row`;
  - il redeschide dupa `sign out / sign in`;
  - separa owner-ul de guest si de alt customer;
  - business understanding este transformat in obiect persistent, nu doar in blocker pedagogic.
- de ce pachetul local nu il depaseste:
  - aici lipsesc complet `order row`, `invoice row`, `receipt identity`.

### 2. `atf5-2026-06-17_13.09.34`

- overall: `92`
- de ce sta sus:
  - are obiect persistent pozitiv real: `message row`;
  - include redeschidere in `message detail`;
  - demonstreaza ownership pozitiv real in interiorul contului.
- de ce pachetul local nu il depaseste:
  - pachetul local are blocker mai sever, dar nu are obiect pozitiv persistent.

### 3. `atf5-2026-06-17_13.59.19`

- overall: `91`
- de ce sta sus:
  - maximizeaza business understanding pozitiv pe lane-ul `guest checkout -> guest identity -> billing -> payment success`;
  - graful de business este real si aplicatia este vizibila ca produs, nu doar ca suita de teste;
  - lipseste closure-ul greu, dar exista progres business pozitiv clar.
- de ce pachetul local nu il depaseste overall:
  - pachetul local este mai puternic pe blocker inevitability;
  - totusi lane-ul pozitiv guest pana la `payment success` ramane business substance mai mare decat blocker-only ownership gap.

### 4. `atf3-2026-06-17_12.58.46`

- overall: `90`
- de ce sta bine:
  - secventa corecta `payment + relogin + reopen` este respectata;
  - verdictul este onest si bine plafonat.
- unde pachetul local este mai bun:
  - `Executive Context` este mai compact si mai putin obositor;
  - `Historical Reopen Strip`, `State Spine`, `Blocker Depth Matrix` si `Proof Mapping` sunt mai clar articulate;
  - blockerul este mai pedagogic si mai inevitabil in citire.

### 5. `atf1-2026-06-17_12.39.34`

- overall: `90`
- de ce sta bine:
  - refresh complet de proof chain, 5 / 5 runs, 25 / 25 tests, contract rece bun;
  - cap onest: nu vinde refresh-ul ca frontiera noua.
- unde pachetul local este mai bun:
  - raportul local explica mai bine overlay-ul blockerului peste harta aplicatiei;
  - lipsa obiectului este mai bine structurata in `Blocker Depth Matrix`;
  - partea superioara a raportului este mai aerisita si mai executiva.

### 6. `atf6-2026-06-17_12.05.36`

- overall: `90`
- de ce sta bine:
  - blocker profund bun, complet, 5 / 5 runs si 25 / 25 tests.
- unde pachetul local este mai bun:
  - aceeasi familie este dusa mai departe in raportare;
  - proof mapping-ul este mai explicit;
  - graph-ul este mai minimalist si mai business-first;
  - `analysis/link-audit.md` si README-ul local explica mai bine postura rece a pachetului.

## Scor comparativ pe axele mari

| Pachet | Substance | Graph realism | Reopenability | Proof chain | Contract | Overall | Verdict scurt |
|---|---:|---:|---:|---:|---:|---:|---|
| `atf5-2026-06-17_12.08.06` | 89 | 92 | 94 | 94 | 94 | 92 | pozitiv persistent pe `favorite row` |
| `atf5-2026-06-17_13.09.34` | 89 | 92 | 94 | 94 | 94 | 92 | pozitiv persistent pe `message row` |
| `atf5-2026-06-17_13.59.19` | 88 | 91 | 94 | 94 | 94 | 91 | progres guest checkout real, fara closure greu |
| `ATF6 ROOT_RUN 2026-06-17_14.54.17` | 86 | 92 | 95 | 95 | 95 | 90 | blocker profund, inevitabil, foarte bine redeschisibil |
| `atf3-2026-06-17_12.58.46` | 86 | 90 | 94 | 94 | 94 | 90 | blocker bun pe `payment + relogin` |
| `atf1-2026-06-17_12.39.34` | 85 | 90 | 95 | 94 | 94 | 90 | refresh rece bun, nu salt business nou |
| `atf6-2026-06-17_12.05.36` | 85 | 90 | 94 | 94 | 94 | 90 | blocker benchmark mai vechi, mai putin pedagogic |

## Top rece actual

1. `atf5-2026-06-17_12.08.06` -> `92`
2. `atf5-2026-06-17_13.09.34` -> `92`
3. `atf5-2026-06-17_13.59.19` -> `91`
4. `ATF6 ROOT_RUN 2026-06-17_14.54.17` -> `90`
5. `atf3-2026-06-17_12.58.46` -> `90`
6. `atf1-2026-06-17_12.39.34` -> `90`
7. `atf6-2026-06-17_12.05.36` -> `90`

## De ce pachetul local urca in top intre blocker benchmarks

- harta de business este mai clara: `Catalog -> Cart -> Checkout -> Payment success -> Account -> Orders / Invoices / Receipt`;
- overlay-ul blockerului vine dupa harta aplicatiei, nu in locul ei;
- `Blocker Depth Matrix` transforma absenta intr-o matrice business-first, nu doar intr-o lista de lipsuri;
- `Proof Mapping` pune `claim -> run diff -> Extent -> XML -> run-state` fara sa forteze cititorul sa caute singur;
- `Historical Reopen Strip` si `State Spine` fac pachetul mai usor de repornit rece decat multe exemple bune, dar mai vechi.

## Ce mai tine jos nota

- nu exista obiect persistent pozitiv real;
- lipsesc exact cele mai grele obiecte de ownership: `order row`, `invoice row`, `receipt identity / order id`;
- blockerul este excelent explicat, dar explicatia nu poate inlocui closure-ul pozitiv.

## Maxim legitim fara run nou

- `Overall` legitim ramane `90`;
- pachetul poate depasi unele exemple `html-ex` la calitatea raportarii si la pedagogia blockerului;
- pachetul nu poate depasi onest pachetele `92` sau `91` care detin business progress pozitiv real.

## Verdict rece final

- pachetul local este acum un benchmark foarte puternic pentru `deep post-purchase blocker`;
- ca raportare rece si business graph poate depasi o parte din `html-ex`;
- ca overall ramane corect la `90`, sub orice pachet cu obiect persistent pozitiv real.
