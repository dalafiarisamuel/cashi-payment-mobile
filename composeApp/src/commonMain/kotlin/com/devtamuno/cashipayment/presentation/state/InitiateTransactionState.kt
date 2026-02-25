package com.devtamuno.cashipayment.presentation.state

sealed class InitiateTransactionState {
    data object Idle : InitiateTransactionState()

    data object Loading : InitiateTransactionState()

    data object Success : InitiateTransactionState()

    data class Failure(val message: String) : InitiateTransactionState()

    val isSuccess: Boolean get() = this is Success
    val isLoading: Boolean get() = this is Loading
    val isFailure: Boolean get() = this is Failure
    val isIdle: Boolean get() = this is Idle
}
