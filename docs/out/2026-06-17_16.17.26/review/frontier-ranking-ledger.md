# ROOT_RUN 2026-06-17_16.17.26

## Question
Ce winner rece a fost ales pentru acest ROOT_RUN?

## Answer
$winner

## Question
Ce NU mostenesc din memorie sau html-ex?

## Answer
Nu mostenesc business, scor sau verdict copiate. Folosesc html-ex doar pentru severitate, forma, diff spine si publicare.

## Question
De ce acest winner bate alte frontiere locale?

## Answer
Pentru ca muta ruptura mai sus decat orders empty / invoices empty: la primul obiect comercial care ar fi trebuit sa existe imediat dupa payment success.

## Question
Care este fallback-ul rece?

## Answer
$fallback
"@ | Set-Content -Path (Join-Path C:\work\ex\java\demo\atf1\test-framework\docs\out\2026-06-17_16.17.26\review 'round-pretraining-brief.md') -Encoding UTF8

@"
# frontier-ranking-ledger

1. winner = $winner
   - beats invoice-only replay because it attacks the first missing commercial identity
   - beats route-only replay because route reachability without object persistence is weaker business truth
2. fallback = $fallback
3. rejected now = pure invoice-empty variants without narrowing the contradiction upstream
4. verdict = launch
