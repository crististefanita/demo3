# Prompt Snapshot

- command = `mvn "-Dtest=com.endava.ai.atf.ui.RegistrationTests#negative_missing_required_fields_shows_error+negative_invalid_email_should_fail+negative_weak_password_should_show_validation_message+negative_phone_with_letters_should_fail" test`
- why this run exists = freeze the parent negative-validation baseline before claiming any happy-path or descendant lift
