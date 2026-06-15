# Frontier Ranking Ledger

## Candidati reci

| Frontiera | Presiune business | Densitate pentru `5 / 5` | Stare runtime | Verdict |
| --- | --- | --- | --- | --- |
| `hub cont si identitate` | mare | buna | tine | `kept` |
| `favorites ownership si persistenta` | mare | buna | tine | `kept` |
| `recovery public si confirmare` | medie spre mare | buna | tine | `kept dupa rerank` |
| `message reply progression` | foarte mare | buna | cade `3 / 5` | `respins` |
| `checkout orchestration autentificata` | mare | buna | tine | `kept` |
| `checkout continuity post-success` | mare | buna | tine | `kept` |
| `password change credential closure` | foarte mare | buna | cade `1 / 5` | `respins` |
| `2FA protected surfaces` | medie | buna | verde pe rerulare izolata | `nepromovat` |

## Alegere rece

- winner pachet: `breadth de self-service de cont, recovery si checkout`
- motiv:
  - rupe auth-heavy-ul precedent
  - acopera trei macro-zone reale ale aplicatiei
  - consuma `5 / 5` sloturi kept fara a minti frontierele grele cazute
- de ce `2FA protected surfaces` nu intra:
  - a iesit verde izolat, dar ar fi ingustat breadth-ul castigator
  - nu bate rece combinatia deja consumata `cont + favorites + recovery + checkout`
