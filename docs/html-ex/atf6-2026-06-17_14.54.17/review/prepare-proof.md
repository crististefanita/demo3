# Prepare Proof

- source truth first: `review/root-source-thin-no-index.diff`
- state spine: `review/package-state.json`, `runs/run_0x/review/run-state.json`, `review/execution-gate-card.md`
- UI proof per slot: `runs/run_0x/validation/reports/ExtentReport_*.html`
- machine proof per slot: `runs/run_0x/validation/TEST-*.xml`
- command carrier per slot: `runs/run_0x/validation/command-output.txt` and `command-result.md`
- final page must map major claims in order: `claim -> run diff -> Extent -> XML -> run-state`
