package com.devtamuno.cashipayment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devtamuno.cashipayment.domain.model.Payment
import com.devtamuno.cashipayment.domain.usecase.GetAllPaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val getAllPaymentUseCase: GetAllPaymentUseCase,
) : ViewModel() {

  private val _payments = MutableStateFlow<List<Payment>>(emptyList())
  val payments: StateFlow<List<Payment>> = _payments.asStateFlow()

  private val _isSheetVisible = MutableStateFlow(false)
  val isSheetVisible: StateFlow<Boolean> = _isSheetVisible.asStateFlow()

  init {
    observePayments()
  }

  private fun observePayments() {
    viewModelScope.launch { getAllPaymentUseCase().collectLatest { _payments.value = it } }
  }

  fun showPaymentSheet() {
      _isSheetVisible.value = true
  }

  fun hidePaymentSheet() {
      _isSheetVisible.value = false
  }
}
