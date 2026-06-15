# run_03 - materializare si durabilitate mesaje

- claim business: firul de suport materializat ramane gasibil, redeschis si coerent chiar dupa relogin si dupa un reply care nu avanseaza starea
- familie: `empty guidance + materializare + relogin + reopen + reply gap`
- rezultat: `5 / 5`, `pass`
- de ce testele stau bine impreuna:
  - toate apartin aceluiasi journey de suport in cont
- delta rece:
  - fara delta Java noua
  - business-ul a fost racit la `reply gap`, nu vandut fals ca `progression`
