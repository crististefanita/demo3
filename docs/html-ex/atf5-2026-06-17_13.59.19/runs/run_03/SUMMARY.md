# RUN_03

- claim: Billing address becomes a real owned checkout object and unlocks payment options.
- expected delta: The app should retain concrete billing fields and expose the payment step only after billing completion.
- actual delta: Country, postal code, house number, city, and state remain owned, and payment opens with multiple real options.
- verdict: owned object