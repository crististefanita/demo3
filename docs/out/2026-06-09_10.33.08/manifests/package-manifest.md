# Package Manifest

- timestamp package = docs/out/2026-06-09_10.33.08
- META_ITERATION_COUNT = 10
- active governed window = run_01 .. run_10
- countable kept runs = run_01 .. run_10
- machine total = 10 passed / 0 failed / 0 skipped
- package completeness = complete
- source authority = strong local / clean sandbox

| Run | Role | Result | Countable | Class |
|---|---|---:|---|---|
| run_01 | NL postal and house autofill | 1 / 1 | yes | com.endava.ai.atf.ui.RegistrationAddressAutofillTests#nl_postal_house_autofills_address_fields |
| run_02 | same-input country recomputation | 1 / 1 | yes | com.endava.ai.atf.ui.RegistrationAddressAutofillTests#changing_country_recalculates_autofilled_address |
| run_03 | syntactic NL input still materializes | 1 / 1 | yes | com.endava.ai.atf.ui.RegistrationAddressAutofillTests#synthetic_nl_postal_house_still_materializes_address |
| run_04 | submit succeeds with autofilled address | 1 / 1 | yes | com.endava.ai.atf.ui.RegistrationAddressAutofillTests#registration_succeeds_with_autofilled_address |
| run_05 | baseline positive registration redirect | 1 / 1 | yes | com.endava.ai.atf.ui.RegistrationTests#positive_registration_redirects_to_login |
| run_06 | missing required fields show error | 1 / 1 | yes | com.endava.ai.atf.ui.RegistrationTests#negative_missing_required_fields_shows_error |
| run_07 | invalid email rejected | 1 / 1 | yes | com.endava.ai.atf.ui.RegistrationTests#negative_invalid_email_should_fail |
| run_08 | duplicate email rejected | 1 / 1 | yes | com.endava.ai.atf.ui.RegistrationTests#negative_duplicate_email_should_fail |
| run_09 | weak password policy feedback | 1 / 1 | yes | com.endava.ai.atf.ui.RegistrationTests#negative_weak_password_should_show_validation_message |
| run_10 | phone letters rejected | 1 / 1 | yes | com.endava.ai.atf.ui.RegistrationTests#negative_phone_with_letters_should_fail |