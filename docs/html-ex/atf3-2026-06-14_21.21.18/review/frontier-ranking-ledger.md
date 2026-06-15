# Frontier ranking ledger

## Winner

- `authenticated self-service ownership breadth`

## De ce castiga

- Rupe replay-ul comod pe `security-only`, dar nu evadeaza in `public pre-purchase`.
- Are 5 lane-uri naturale distincte si deja materializate in clase live de cate `5` teste:
  - cont
  - mesaje
  - plata
  - istoric post-purchase
  - ownership gate prin securitate/TOTP
- Permite un business graph de aplicatie real, nu doar un jurnal de familii testate.

## De ce pierde `security-only`

- Tocmai a fost inchis procedural in ultimul pachet local.
- Ramane greu, dar prea ingust daca il relansez imediat ca familie dominanta.

## De ce pierde `messages-only`

- Ar consuma bine un slot, dar ar lasa breadth-ul prea subtire.
- Risc mare sa vanda persistence locala drept owner al intregului self-service.

## De ce pierde `payment-only`

- Ar umfla aceeasi macro-familie si ar suna fals mai lat decat este.
- Risc mare de `five payment variants = false breadth`.

## De ce pierde `public pre-purchase`

- Este macro-zona deja consumata.
- Ar fi exact intoarcerea comoda interzisa de standard.
