# RUN_01

- claim: Guest checkout opens a real guest-identity step from a populated cart.
- expected delta: Guest cart ownership should materialize into guest identity without faking billing ownership.
- actual delta: The app exposes guest email and name fields and preserves cart total, but guest submit alone does not yet expose billing address.
- verdict: owned boundary