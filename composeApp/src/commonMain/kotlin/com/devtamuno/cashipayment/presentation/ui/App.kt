package com.devtamuno.cashipayment.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.devtamuno.cashipayment.presentation.ui.screen.payment.PaymentDestination
import com.devtamuno.cashipayment.presentation.ui.screen.payment.PaymentScreen
import com.devtamuno.cashipayment.presentation.ui.screen.receipt.ReceiptScreen
import com.devtamuno.cashipayment.presentation.ui.theme.CashiPaymentTheme
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

private val config = SavedStateConfiguration {
  serializersModule = SerializersModule {
    polymorphic(NavKey::class) {
      subclass(PaymentDestination.HomeScreen::class, PaymentDestination.HomeScreen.serializer())
      subclass(
          PaymentDestination.ReceiptScreen::class,
          PaymentDestination.ReceiptScreen.serializer(),
      )
    }
  }
}

@Composable
@Preview
fun App() {
  CashiPaymentTheme {
    val backStack = rememberNavBackStack(config, PaymentDestination.HomeScreen)

    NavDisplay(
        backStack = backStack,
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
        entryProvider =
            entryProvider {
              entry<PaymentDestination.HomeScreen> {
                PaymentScreen { backStack.add(PaymentDestination.ReceiptScreen(it)) }
              }
              entry<PaymentDestination.ReceiptScreen> { backStackEntry ->
                val payment = backStackEntry.payment
                ReceiptScreen(
                    payment = payment,
                    onBack = { backStack.removeLastOrNull() },
                )
              }
            },
    )
  }
}
