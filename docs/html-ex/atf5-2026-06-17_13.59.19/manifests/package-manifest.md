# Package manifest

- runtime: docs/prompts/iterativ/agent-runtime.properties
- root: $ts
- winner macro lane: guest checkout -> billing -> payment success
- strongest unresolved truth: order-number and invoice closure absent on payment-success surface
- run count: 5
- total tests produced: 25
"@

Write-Text "C:\work\ex\java\demo\atf5\test-framework\docs\out\2026-06-17_13.59.19\prompt\input-snapshot.md" @"
# Input snapshot

Sources read live before execution:

1. docs/prompts/iterativ/agent-runtime.properties
2. docs/prompts/iterativ/iterative-core-standard.html
3. docs/prompts/iterativ/prompt-evolution-orchestration-standard.html
4. docs/prompts/iterativ/reporting.html
5. docs/prompts/iterativ/score-overview-reference.html
6. docs/prompts/iterativ/business-flow-graph-rubric.html
7. docs/prompts/iterativ/ui-flow-discovery-and-atf-test-generation.html
8. docs/out/README.md
9. C:/work/ex/java/demo/common/docs/html-ex/README.md
10. C:/work/ex/java/demo/common/docs/html-ex/atf5-2026-06-17_13.09.34/README.md
11. C:/work/ex/java/demo/common/docs/html-ex/atf5-2026-06-17_12.08.06/README.md
12. docs/Living_Architecture_UI_API_doc_v1_0.html
"@

Write-Text "C:\work\ex\java\demo\atf5\test-framework\docs\out\2026-06-17_13.59.19\review\round-pretraining-brief.md" @"
# Round pretraining brief

candidate frontier = guest checkout -> billing -> payment -> order-number closure
why it is new business = The previous active local references were avorite row and message row; this family targets a second commerce object inside checkout.
why it beats alternatives = It moves directly toward order row / invoice row pressure instead of revalidating the old favorite/message families.
required proof = guest identity ownership, billing ownership, payment success, then order-number presence or honest absence
what is inherited cold = score discipline, graph shape, diff-first truth, state files, and reporting contract
what is not inherited = no reuse of previous winner claims; no score uplift from old persistent objects