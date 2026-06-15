# run_02 - ownership favorites si persistenta

- claim business: favorites trece prin empty state, materializare, acumulare si persistenta dupa relogin
- familie: `favorites empty state + favorites page + accumulation + relogin persistence + accumulation relogin`
- rezultat: `5 / 5`, `pass`
- de ce testele stau bine impreuna:
  - toate apartin aceleiasi functii reale de ownership pe favorites
  - combina intrarea de la zero cu persistenta dupa reautentificare
- delta rece:
  - nu exista delta Java noua
  - run-ul transforma favorites din ruta de cont in comportament de ownership
