# Source Delta Carrier

Run-ul foloseste aceeasi ancora `/contact`, dar muta verdictul in contractul de submit:

- `submitEmptyContactForm()` creeaza presiunea minima pe formular;
- `assertRequiredValidationMessagesVisible()` fixeaza rece feedback-ul de validare;
- clasa `ContactSupportValidationTests` publica acest feedback drept familie coerenta de reguli, nu drept cinci click-uri arbitrare.
