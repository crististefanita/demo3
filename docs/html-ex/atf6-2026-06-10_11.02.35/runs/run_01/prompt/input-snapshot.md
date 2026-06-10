# Input Snapshot

- command = `mvn "-Dtest=com.endava.ai.atf.ui.AuthenticatedProfileTests#positive_registered_user_can_open_profile_form_with_prefilled_registration_data" test`
- why this run exists = fresh-round reranking reopened authenticated breadth after the previous package blocked same-round continuation at account overview
- chosen slice = `profile`
- why this slice won = it proves persisted identity and address continuity across multiple form fields plus explicit update action in one run
- sibling demotions:
  - `favorites` = weaker fresh-user falsifier than persisted profile data
  - `invoices` = weaker branch-local proof for a fresh user than persisted profile data
  - `messages` = lower-value sibling and not stronger than the top three frontier rows
