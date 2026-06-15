# run_03 - recovery public si confirmare

- claim business: un vizitator poate intra rece in recovery, iar clientul inregistrat poate ajunge la confirmarea de resetare dupa guard si retry corect
- familie: `forgot password route + required email + invalid email + confirmation + retry to confirmation`
- rezultat: `5 / 5`, `pass`
- de ce testele stau bine impreuna:
  - toate confirma podul dintre public si re-entry in cont
  - toate stau pe aceeasi suprafata vizibila de recovery
- delta rece:
  - acest run a intrat dupa rerank, in locul frontierei de mesagerie care a cazut la rerulare
  - lateste pachetul dincolo de self-service autentic pur
