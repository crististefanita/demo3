# Manifest run_04

- familie: checkout-autentificat-si-gap-factura
- comanda: mvn -q "-Dtest=com.endava.ai.ui.tests.AuthenticatedCheckoutBillingAutofillTests,com.endava.ai.ui.tests.AuthenticatedCheckoutPaymentGuardTests,com.endava.ai.ui.tests.AuthenticatedCheckoutCashOnDeliveryTests,com.endava.ai.ui.tests.AuthenticatedCheckoutInvoiceGapTests,com.endava.ai.ui.tests.AuthenticatedCheckoutInvoiceGapReloginTests" test
- teste incluse:
  - com.endava.ai.ui.tests.AuthenticatedCheckoutBillingAutofillTests
  - com.endava.ai.ui.tests.AuthenticatedCheckoutPaymentGuardTests
  - com.endava.ai.ui.tests.AuthenticatedCheckoutCashOnDeliveryTests
  - com.endava.ai.ui.tests.AuthenticatedCheckoutInvoiceGapTests
  - com.endava.ai.ui.tests.AuthenticatedCheckoutInvoiceGapReloginTests
