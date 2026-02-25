package com.devtamuno.cashipayment.presentation.ui.screen.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cashipayment.composeapp.generated.resources.Res
import cashipayment.composeapp.generated.resources.app_name
import cashipayment.composeapp.generated.resources.make_payment
import cashipayment.composeapp.generated.resources.transaction_history
import com.devtamuno.cashipayment.domain.model.Payment
import com.devtamuno.cashipayment.presentation.state.InitiateTransactionState
import com.devtamuno.cashipayment.presentation.ui.component.EmptyHistory
import com.devtamuno.cashipayment.presentation.ui.component.PaymentHistoryItem
import com.devtamuno.cashipayment.presentation.ui.component.PaymentSuccessDialog
import com.devtamuno.cashipayment.presentation.viewmodel.PaymentFormViewModel
import com.devtamuno.cashipayment.presentation.viewmodel.PaymentViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Suppress("ParamsComparedByRef")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel = koinInject<PaymentViewModel>(),
    formViewModel: PaymentFormViewModel = koinInject<PaymentFormViewModel>(),
    modifier: Modifier = Modifier,
    navigateToReceiptScreen: (Payment) -> Unit,
) {
  val payments by viewModel.payments.collectAsStateWithLifecycle()
  val isSheetVisible by viewModel.isSheetVisible.collectAsStateWithLifecycle()
  val transactionState by formViewModel.transactionState.collectAsStateWithLifecycle()

  var showSuccessDialog by remember { mutableStateOf(false) }

  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  LaunchedEffect(transactionState) {
    if (transactionState is InitiateTransactionState.Success) {
      viewModel.hidePaymentSheet()
      showSuccessDialog = true
    }
  }

  Scaffold(
      modifier = modifier.fillMaxSize(),
      contentWindowInsets = WindowInsets.safeContent,
      floatingActionButton = {
        if (!isSheetVisible) {
          FloatingActionButton(
              onClick = { viewModel.showPaymentSheet() },
              containerColor = MaterialTheme.colorScheme.primary,
              contentColor = MaterialTheme.colorScheme.onPrimary,
              shape = CircleShape,
          ) {
            Icon(
                Icons.Rounded.Payments,
                contentDescription = stringResource(Res.string.make_payment),
            )
          }
        }
      },
      topBar = {
        Surface {
          Column(
              modifier =
                  Modifier.fillMaxWidth()
                      .padding(
                          top =
                              WindowInsets.safeContent.asPaddingValues().calculateTopPadding() +
                                  16.dp,
                          start = 16.dp,
                          end = 16.dp,
                          bottom = 16.dp,
                      )
          ) {
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = stringResource(Res.string.transaction_history),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline,
            )
          }
        }
      },
  ) { paddingValues ->
    if (payments.isEmpty()) {
      EmptyHistory(modifier = Modifier.padding(paddingValues))
    } else {
      LazyColumn(
          modifier = Modifier.fillMaxSize(),
          contentPadding =
              PaddingValues(
                  start = 16.dp,
                  top = paddingValues.calculateTopPadding() + 8.dp,
                  end = 16.dp,
                  bottom = paddingValues.calculateBottomPadding() + 16.dp,
              ),
          verticalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        items(payments) { payment ->
          PaymentHistoryItem(payment) { navigateToReceiptScreen(payment) }
        }
      }
    }
  }

  if (showSuccessDialog) {
    PaymentSuccessDialog {
      showSuccessDialog = false
      formViewModel.resetForm()
    }
  }

  if (isSheetVisible) {
    ModalBottomSheet(
        onDismissRequest = {
          if (!transactionState.isLoading) {
            viewModel.hidePaymentSheet()
            formViewModel.resetForm()
          }
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        properties =
            ModalBottomSheetProperties(
                shouldDismissOnBackPress = !transactionState.isLoading,
            ),
    ) {
      PaymentFormContentDialog(
          viewModel = formViewModel,
      )
    }
  }
}
