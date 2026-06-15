# run_01 - hub cont si identitate

- claim business: clientul autentificat intra in zona de cont si isi regaseste identitatea, rutele de administrare si primul ownership
- familie: `overview + profile + invoices + messages + favorites`
- rezultat: `5 / 5`, `pass`
- de ce testele stau bine impreuna:
  - toate pornesc din intrarea in cont
  - toate confirma ca zona de self-service apartine utilizatorului autentificat
- delta rece:
  - nu exista delta Java noua
  - run-ul ingheata auditabil o familie de business care tinea deja in cod
