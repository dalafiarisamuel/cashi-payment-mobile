package com.devtamuno.cashipayment.presentation.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devtamuno.cashipayment.domain.model.Currency
import com.devtamuno.cashipayment.domain.model.Payment
import com.devtamuno.cashipayment.domain.model.TransactionStatus
import com.devtamuno.cashipayment.domain.usecase.InitiateTransactionUseCase
import com.devtamuno.cashipayment.presentation.state.InitiateTransactionState
import com.devtamuno.cashipayment.shared.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PaymentFormViewModel(
    private val initiateTransactionUseCase: InitiateTransactionUseCase,
) : ViewModel() {

  private val _transactionState =
      MutableStateFlow<InitiateTransactionState>(InitiateTransactionState.Idle)
  val transactionState: StateFlow<InitiateTransactionState> = _transactionState.asStateFlow()

  var email by mutableStateOf("")
    private set

  var amount by mutableStateOf("")
    private set

  var currency by mutableStateOf(Currency.SUPPORTED_CURRENCIES.first())
    private set

  val isFormValid by derivedStateOf {
    val tempPayment =
        Payment(
            recipientEmail = email,
            amount = amount.toDoubleOrNull() ?: 0.0,
            currency = "",
            status = TransactionStatus.PENDING,
            timestamp = 0L,
        )
    tempPayment.isValid()
  }

  fun updateEmail(value: String) {
    email = value
  }

  fun updateAmount(value: String) {
    amount = value
  }

  fun updateCurrency(value: String) {
    currency = value
  }

  fun resetForm() {
    email = ""
    amount = ""
    currency = Currency.SUPPORTED_CURRENCIES.first()
    _transactionState.value = InitiateTransactionState.Idle
  }

  fun initiateTransaction() {
    val payment =
        Payment(
            recipientEmail = email,
            amount = amount.toDoubleOrNull() ?: 0.0,
            currency = currency,
            status = TransactionStatus.PENDING,
            timestamp = 0L,
        )
    viewModelScope.launch {
      _transactionState.value = InitiateTransactionState.Loading
      when (val result = initiateTransactionUseCase(payment)) {
        is Resource.Success -> {
          _transactionState.value = InitiateTransactionState.Success
        }

        is Resource.Failure -> {
          _transactionState.value = InitiateTransactionState.Failure(result.error)
        }
      }
    }
  }
}
