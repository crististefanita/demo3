# Input Snapshot

- standard = docs/prompts/iterativ/iterative-core-standard.html
- window slot = run_10
- command = mvn -q "-Dtest=com.endava.ai.atf.ui.RegistrationTests#negative_phone_with_letters_should_fail" test
- target role = phone letters rejected
- scope = public registration invalid-phone boundary