package com.devtamuno.cashipayment.domain.usecase

import com.devtamuno.cashipayment.data.remote.data.toRemote
import com.devtamuno.cashipayment.data.remote.repository.PaymentRepository
import com.devtamuno.cashipayment.domain.model.Payment
import com.devtamuno.cashipayment.shared.util.Resource

open class InitiateTransactionUseCase(private val repository: PaymentRepository) {
  open suspend operator fun invoke(payment: Payment): Resource<Unit> {
    return repository.initiateTransaction(payment.toRemote())
  }
}
