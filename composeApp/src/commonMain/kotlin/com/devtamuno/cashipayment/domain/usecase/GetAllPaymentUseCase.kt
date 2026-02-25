package com.devtamuno.cashipayment.domain.usecase

import com.devtamuno.cashipayment.domain.model.Payment
import com.devtamuno.cashipayment.data.remote.repository.PaymentRepository
import com.devtamuno.cashipayment.data.remote.data.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllPaymentUseCase(
    private val repository: PaymentRepository
) {
    operator fun invoke(): Flow<List<Payment>> {
        return repository.observeTransactions().map { list ->
            list.map { it.toDomain() }
        }
    }
}
