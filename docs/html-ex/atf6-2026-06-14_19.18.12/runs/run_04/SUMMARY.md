# run_04 - checkout autentificat si gap factura

- claim business: clientul autentificat mosteneste identitatea in checkout, trece de guard-uri, finalizeaza `COD`, dar factura nu se materializeaza in ledger
- familie: `billing autofill + payment guard + submit COD + invoice gap + invoice gap dupa relogin`
- rezultat: `5 / 5`, `pass`
- de ce testele stau bine impreuna:
  - toate descriu acelasi journey de cumparare autentificata si efectul lui post-purchase
- delta rece:
  - fara delta Java noua
  - slotul ramane pe claim-urile care tin rece, nu pe `authenticated recognition`
