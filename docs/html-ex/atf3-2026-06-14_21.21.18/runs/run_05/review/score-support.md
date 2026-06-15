# run_05 score support

1. Profilul pretins editat revine la valorile originale dupa enrollment TOTP si challenge relogin.
2. Challenge-ul TOTP se repeta pe al doilea fresh login.
3. Parola veche inca ajunge la challenge dupa change-password pretins reusit.
4. Parola noua ramane respinsa.
5. Parola veche plus cod TOTP live restaureaza ownership-ul complet al contului.
