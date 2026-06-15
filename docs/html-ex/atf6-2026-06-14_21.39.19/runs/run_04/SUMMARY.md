# run_04 - checkout orchestration autentificata

- claim business: clientul autentificat este recunoscut in checkout, billing-ul se completeaza, metodele de plata sunt gardate, iar succesul vizibil nu produce materializare in invoices
- familie: `checkout recognition + billing autofill + payment guard + COD success + invoice gap`
- rezultat: `5 / 5`, `pass`
- de ce testele stau bine impreuna:
  - toate apartin aceleiasi ancore de checkout autentificat
  - combina drumul happy-path cu contradictia centrala post-success
- delta rece:
  - nu exista delta Java noua
  - run-ul deschide tensiunea business dintre succesul vizibil si lipsa de materializare in invoices
