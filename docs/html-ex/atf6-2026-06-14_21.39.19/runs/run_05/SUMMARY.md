# run_05 - checkout continuity post-success

- claim business: dupa succesul de checkout, clientul ramane in aceeasi contradictie de invoices atat la relogin, cat si dupa checkout repetat
- familie: `checkout recognition + COD success + invoice gap + repeated invoice gap + invoice gap after relogin`
- rezultat: `5 / 5`, `pass`
- de ce testele stau bine impreuna:
  - toate apasa exact pe continuitatea post-success
  - toate verifica daca succesul vizibil se transforma sau nu in stare detinuta
- delta rece:
  - nu exista delta Java noua
  - run-ul confirma ca tensiunea din run_04 nu este accident singular
