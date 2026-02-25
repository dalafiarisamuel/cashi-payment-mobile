package com.devtamuno.cashipayment.presentation.ui.screen.payment

import androidx.navigation3.runtime.NavKey
import com.devtamuno.cashipayment.domain.model.Payment
import kotlinx.serialization.Serializable

@Serializable
internal sealed interface PaymentDestination : NavKey {

  @Serializable data object HomeScreen : PaymentDestination

  @Serializable data class ReceiptScreen(val payment: Payment) : PaymentDestination
}
