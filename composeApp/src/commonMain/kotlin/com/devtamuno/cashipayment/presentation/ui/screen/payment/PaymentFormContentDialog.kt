package com.devtamuno.cashipayment.presentation.ui.screen.payment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cashipayment.composeapp.generated.resources.Res
import cashipayment.composeapp.generated.resources.amount
import cashipayment.composeapp.generated.resources.amount_placeholder
import cashipayment.composeapp.generated.resources.email_placeholder
import cashipayment.composeapp.generated.resources.make_payment
import cashipayment.composeapp.generated.resources.recipient_email
import cashipayment.composeapp.generated.resources.send_money
import cashipayment.composeapp.generated.resources.success_exclamation
import com.devtamuno.cashipayment.presentation.state.InitiateTransactionState
import com.devtamuno.cashipayment.presentation.ui.component.CurrencyDropDown
import com.devtamuno.cashipayment.presentation.ui.theme.SuccessGreen
import com.devtamuno.cashipayment.presentation.viewmodel.PaymentFormViewModel
import org.jetbrains.compose.resources.stringResource

@Suppress("ParamsComparedByRef")
@Composable
fun PaymentFormContentDialog(
    viewModel: PaymentFormViewModel,
) {

  val transactionState by viewModel.transactionState.collectAsStateWithLifecycle()
  val keyboardController = LocalSoftwareKeyboardController.current

  Column(
      modifier = Modifier.padding(horizontal = 24.dp).padding(bottom = 48.dp).fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(20.dp),
  ) {
    Text(
        text = stringResource(Res.string.send_money),
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
    )

    OutlinedTextField(
        value = viewModel.email,
        onValueChange = viewModel::updateEmail,
        label = {
          Text(
              stringResource(Res.string.recipient_email),
              style = MaterialTheme.typography.bodySmall,
          )
        },
        placeholder = {
          Text(
              stringResource(Res.string.email_placeholder),
              style = MaterialTheme.typography.bodySmall,
          )
        },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        textStyle = MaterialTheme.typography.bodySmall,
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
      OutlinedTextField(
          value = viewModel.amount,
          onValueChange = viewModel::updateAmount,
          label = {
            Text(
                stringResource(Res.string.amount),
                style = MaterialTheme.typography.bodySmall,
            )
          },
          placeholder = {
            Text(
                stringResource(Res.string.amount_placeholder),
                style = MaterialTheme.typography.bodySmall,
            )
          },
          modifier = Modifier.weight(1f),
          singleLine = true,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
          textStyle = MaterialTheme.typography.bodySmall,
      )

      CurrencyDropDown(
          selectedCurrency = viewModel.currency,
          onCurrencySelected = viewModel::updateCurrency,
          modifier = Modifier.weight(0.6f),
      )
    }

    AnimatedVisibility(visible = transactionState.isSuccess) {
      Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            tint = SuccessGreen,
            modifier = Modifier.size(24.dp),
        )
        Text(
            stringResource(Res.string.success_exclamation),
            color = SuccessGreen,
            fontWeight = FontWeight.Medium,
        )
      }
    }

    if (transactionState is InitiateTransactionState.Failure) {
      Text(
          text = (transactionState as InitiateTransactionState.Failure).message,
          color = MaterialTheme.colorScheme.error,
          style = MaterialTheme.typography.bodySmall,
          modifier = Modifier.padding(top = 8.dp),
      )
    }

    Button(
        onClick = {
          keyboardController?.hide()
          viewModel.initiateTransaction()
        },
        modifier = Modifier.fillMaxWidth().height(56.dp),
        enabled = !transactionState.isLoading && viewModel.isFormValid,
        shape = MaterialTheme.shapes.medium,
    ) {
      if (transactionState.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            strokeWidth = 2.dp,
        )
      } else {
        Text(
            stringResource(Res.string.make_payment),
            style =  MaterialTheme.typography.bodyMedium.copy(letterSpacing = 0.sp),
            fontWeight = FontWeight.SemiBold,
        )
      }
    }
  }
}
