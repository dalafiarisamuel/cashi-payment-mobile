package com.devtamuno.cashipayment.data.remote.repository

import com.devtamuno.cashipayment.shared.util.Resource
import com.devtamuno.cashipayment.data.remote.data.PaymentRemote
import com.devtamuno.cashipayment.data.remote.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal actual class PaymentRepositoryImpl : PaymentRepository {

  actual override suspend fun initiateTransaction(payment: PaymentRemote): Resource<Unit> {
    return Resource.Success(Unit)
  }

  actual override fun observeTransactions(): Flow<List<PaymentRemote>> {
    return emptyFlow()
  }
}
