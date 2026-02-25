package com.devtamuno.cashipayment.data.remote.repository

import com.devtamuno.cashipayment.data.remote.data.PaymentRemote
import com.devtamuno.cashipayment.shared.util.Resource
import kotlinx.coroutines.flow.Flow

internal expect class PaymentRepositoryImpl : PaymentRepository {
  override suspend fun initiateTransaction(payment: PaymentRemote): Resource<Unit>

  override fun observeTransactions(): Flow<List<PaymentRemote>>
}
