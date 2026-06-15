# Manifest run_01

- familie: hub-cont-si-identitate
- comanda: mvn -q "-Dtest=com.endava.ai.ui.tests.AuthenticatedAccountOverviewTests,com.endava.ai.ui.tests.AuthenticatedAccountInvoicesTests,com.endava.ai.ui.tests.AuthenticatedAccountMessagesTests,com.endava.ai.ui.tests.AuthenticatedAccountFavoritesTests,com.endava.ai.ui.tests.RegisteredCustomerProfilePersistenceTests" test
- teste incluse:
  - com.endava.ai.ui.tests.AuthenticatedAccountOverviewTests
  - com.endava.ai.ui.tests.AuthenticatedAccountInvoicesTests
  - com.endava.ai.ui.tests.AuthenticatedAccountMessagesTests
  - com.endava.ai.ui.tests.AuthenticatedAccountFavoritesTests
  - com.endava.ai.ui.tests.RegisteredCustomerProfilePersistenceTests
