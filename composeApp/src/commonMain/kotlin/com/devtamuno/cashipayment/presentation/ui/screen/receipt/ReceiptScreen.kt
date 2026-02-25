@file:OptIn(ExperimentalTime::class)

package com.devtamuno.cashipayment.presentation.ui.screen.receipt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cashipayment.composeapp.generated.resources.Res
import cashipayment.composeapp.generated.resources.back
import cashipayment.composeapp.generated.resources.cashi_wallet
import cashipayment.composeapp.generated.resources.done
import cashipayment.composeapp.generated.resources.payment_method
import cashipayment.composeapp.generated.resources.payment_receipt
import cashipayment.composeapp.generated.resources.recipient
import cashipayment.composeapp.generated.resources.status
import cashipayment.composeapp.generated.resources.transaction_id
import com.devtamuno.cashipayment.domain.model.Payment
import com.devtamuno.cashipayment.domain.model.TransactionStatus
import com.devtamuno.cashipayment.domain.model.formatTimestamp
import com.devtamuno.cashipayment.presentation.ui.component.getStatusStyle
import com.devtamuno.cashipayment.presentation.ui.theme.CashiPaymentTheme
import kotlin.time.ExperimentalTime
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiptScreen(payment: Payment, onBack: () -> Unit) {

  val (statusIcon, statusColor, statusText) = payment.getStatusStyle()

  Scaffold(
      topBar = {
        CenterAlignedTopAppBar(
            title = {
              Text(stringResource(Res.string.payment_receipt), fontWeight = FontWeight.Bold)
            },
            navigationIcon = {
              IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(Res.string.back),
                )
              }
            },
        )
      }
  ) { paddingValues ->
    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      
      Box(
          modifier =
              Modifier.size(80.dp).clip(CircleShape).background(statusColor.copy(alpha = 0.1f)),
          contentAlignment = Alignment.Center,
      ) {
        Icon(
            imageVector = statusIcon,
            contentDescription = null,
            tint = statusColor,
            modifier = Modifier.size(40.dp),
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
          text = statusText,
          style = MaterialTheme.typography.headlineSmall,
          fontWeight = FontWeight.Bold,
          color = statusColor,
      )

      Text(
          text = payment.formatTimestamp(),
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.outline,
      )

      Spacer(modifier = Modifier.height(32.dp))

      Text(
          text = "${payment.amount} ${payment.currency}",
          style = MaterialTheme.typography.displayMedium,
          fontWeight = FontWeight.ExtraBold,
          color = MaterialTheme.colorScheme.onSurface,
      )

      Spacer(modifier = Modifier.height(32.dp))

      Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors =
              CardDefaults.cardColors(
                  containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
              ),
      ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
          ReceiptRow(label = stringResource(Res.string.recipient), value = payment.recipientEmail)
          ReceiptRow(
              label = stringResource(Res.string.status),
              value = payment.status,
              valueColor = statusColor,
          )
          ReceiptRow(
              label = stringResource(Res.string.transaction_id),
              value = "TXN-${payment.timestamp}",
          )
          ReceiptRow(
              label = stringResource(Res.string.payment_method),
              value = stringResource(Res.string.cashi_wallet),
          )
        }
      }

      Spacer(modifier = Modifier.weight(1f))

      Button(
          onClick = onBack,
          modifier = Modifier.fillMaxWidth().height(56.dp),
          shape = RoundedCornerShape(12.dp),
      ) {
        Text(stringResource(Res.string.done), fontWeight = FontWeight.Bold)
      }
    }
  }
}

@Composable
private fun ReceiptRow(
    label: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface,
) {
  Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
        text = label,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.outline,
    )
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(
          text = value,
          style = MaterialTheme.typography.bodyLarge,
          fontWeight = FontWeight.SemiBold,
          color = valueColor,
          textAlign = TextAlign.End,
      )
    }
  }
}

@Composable
@Preview
fun ReceiptScreenPreview() {
  CashiPaymentTheme {
    ReceiptScreen(
        payment =
            Payment(
                recipientEmail = "alex.doe@example.com",
                amount = 1250.0,
                currency = "USD",
                status = TransactionStatus.SUCCESS,
                timestamp = 1715684400000L,
            ),
        onBack = {},
    )
  }
}
