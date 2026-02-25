package com.devtamuno.cashipayment.presentation.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cashipayment.composeapp.generated.resources.Res
import cashipayment.composeapp.generated.resources.done
import cashipayment.composeapp.generated.resources.payment_sent
import com.devtamuno.cashipayment.presentation.ui.theme.SuccessGreen
import org.jetbrains.compose.resources.stringResource

@Composable
fun PaymentSuccessDialog(
    onDismiss: () -> Unit,
) {
  AlertDialog(
      onDismissRequest = onDismiss,
      confirmButton = {
        Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) {
          Text(stringResource(Res.string.done))
        }
      },
      icon = {
        Icon(
            Icons.Rounded.CheckCircle,
            contentDescription = null,
            tint = SuccessGreen,
            modifier = Modifier.size(64.dp),
        )
      },
      title = {
        Text(
            text = stringResource(Res.string.payment_sent),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
        )
      },
  )
}
