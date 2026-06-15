# run_02 source delta carrier

- Diff-ul mic al acestui run inchide un lane de self-service autenticat pe persistenta thread-ului, nu doar pe succesul initial al submit-ului.
- Acelasi claim cere mai multe puncte de dovada fiindca thread-ul trece prin reply, list status, relogin si reentry.
