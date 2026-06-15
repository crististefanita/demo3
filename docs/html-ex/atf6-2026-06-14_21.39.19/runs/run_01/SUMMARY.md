# run_01 - hub cont si identitate

- claim business: clientul nou inregistrat isi regaseste hub-ul de cont, profilul si suprafetele principale de self-service imediat dupa primul login
- familie: `account overview + invoices surface + messages surface + favorites route + profile persistence`
- rezultat: `5 / 5`, `pass`
- de ce testele stau bine impreuna:
  - toate confirma intrarea reala in zona de self-service
  - toate stau pe acelasi pod business: `primul login -> cont detinut`
- delta rece:
  - nu exista delta Java noua
  - run-ul ingheata auditabil ownership-ul de cont si identitate, nu doar o ruta izolata
