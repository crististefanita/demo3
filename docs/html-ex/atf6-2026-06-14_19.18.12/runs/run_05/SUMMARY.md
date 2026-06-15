# run_05 - acces protejat dupa 2FA

- claim business: dupa challenge-ul `2FA`, clientul isi pastreaza accesul la overview, profile, favorites, invoices si messages
- familie: `suprafete protejate dupa step-up auth`
- rezultat: `5 / 5`, `pass`
- de ce testele stau bine impreuna:
  - toate verifica acelasi gating business: pasul suplimentar nu rupe ownership-ul suprafetelor din cont
- delta rece:
  - fara delta Java noua
  - slotul completeaza pachetul cu o familie de acces, nu il recentreaza pe securitate
