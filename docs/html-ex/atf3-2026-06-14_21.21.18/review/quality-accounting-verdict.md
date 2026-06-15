# Quality accounting verdict

- `ROUND_TARGET_TEST_COUNT = 5` pe fiecare run: respectat
- `META_ITERATION_COUNT = 5` pe pachet: respectat
- `materially turned into tests = {5 / 5, 5 / 5, 5 / 5, 5 / 5, 5 / 5}`
- `tests produced total = 25`
- `runs consumed = 5 / 5`
- `accounting target met = yes`

## Verdict procedural

- Pachetul a consumat legal toate cele 5 sloturi.
- Nu exista run sub target.
- Hardest lane-ul final pe ownership securizat nu a ramas sub target.

## Verdict de breadth

- `business breadth verdict = partial`
- Motivul rece:
  - pachetul este mai lat decat `security-only` si mai greu decat un replay `public pre-purchase`
  - dar nu inchide tot self-service-ul autentificat al aplicatiei
  - in special, `post-purchase history` publica o contradictie severa, nu un closure sanatos
