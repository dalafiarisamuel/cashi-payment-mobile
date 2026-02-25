package com.devtamuno.cashipayment.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowOutward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devtamuno.cashipayment.domain.model.Payment
import com.devtamuno.cashipayment.domain.model.TransactionStatus
import com.devtamuno.cashipayment.domain.model.formatTimestamp

@Composable
fun PaymentHistoryItem(payment: Payment, onClick: (Payment) -> Unit) {
  val statusStyle = payment.getStatusStyle()

  Card(
      onClick = { onClick(payment) },
      modifier = Modifier.fillMaxWidth(),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
  ) {
    Row(
        modifier = Modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      Box(
          modifier = Modifier.size(30.dp).clip(CircleShape).background(Color.Black),
          contentAlignment = Alignment.Center,
      ) {
        Icon(
            imageVector = Icons.Rounded.ArrowOutward,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(16.dp),
        )
      }

      Column(modifier = Modifier.weight(1f)) {
        Text(
            text = payment.recipientEmail,
            style = MaterialTheme.typography.bodyMedium.copy(letterSpacing = 0.sp),
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = payment.formatTimestamp(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline,
        )
      }

      Column(horizontalAlignment = Alignment.End) {
        Text(
            text = "${payment.amount} ${payment.currency}",
            style = MaterialTheme.typography.bodyMedium.copy(letterSpacing = 0.sp),
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = payment.status,
            style = MaterialTheme.typography.labelSmall,
            color = statusStyle.color,
            fontWeight = FontWeight.Medium,
        )
      }
    }
  }
}

@Preview(name = "Success")
@Composable
fun PaymentHistoryItemSuccessPreview() {
  MaterialTheme {
    PaymentHistoryItem(
        payment =
            Payment(
                recipientEmail = "Samuel Dalafiari",
                amount = 120.0,
                currency = "USD",
                status = TransactionStatus.SUCCESS,
                timestamp = 1678886400000,
            ),
        onClick = {},
    )
  }
}
