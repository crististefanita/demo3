# run_02 - ownership favorites

- claim business: favorites nu este doar ruta goala, ci colectie owned cu persistenta si relogin
- familie: `empty state + acumulare + persistenta dupa relogin`
- rezultat: `5 / 5`, `pass`
- de ce testele stau bine impreuna:
  - toate judeca aceeasi proprietate business: ce se intampla cu colectia personala de favorites
- delta rece:
  - fara delta Java noua
  - plus audit complet pe o familie post-login distincta
