package com.devtamuno.cashipayment.presentation.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.HourglassEmpty
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import cashipayment.composeapp.generated.resources.Res
import cashipayment.composeapp.generated.resources.payment_failed
import cashipayment.composeapp.generated.resources.payment_pending
import cashipayment.composeapp.generated.resources.payment_successful
import com.devtamuno.cashipayment.domain.model.Payment
import com.devtamuno.cashipayment.domain.model.TransactionStatus
import com.devtamuno.cashipayment.presentation.ui.theme.PendingYellow
import com.devtamuno.cashipayment.presentation.ui.theme.SuccessGreen
import org.jetbrains.compose.resources.stringResource

data class PaymentStatusStyle(val icon: ImageVector, val color: Color, val text: String)

@Composable
fun Payment.getStatusStyle(): PaymentStatusStyle {
  return when (status.uppercase()) {
    TransactionStatus.SUCCESS ->
        PaymentStatusStyle(
            Icons.Rounded.Check,
            SuccessGreen,
            stringResource(Res.string.payment_successful),
        )

    TransactionStatus.PENDING ->
        PaymentStatusStyle(
            Icons.Rounded.HourglassEmpty,
            PendingYellow,
            stringResource(Res.string.payment_pending),
        )

    else ->
        PaymentStatusStyle(
            Icons.Rounded.Close,
            MaterialTheme.colorScheme.error,
            stringResource(Res.string.payment_failed),
        )
  }
}
