package com.devtamuno.cashipayment.data.remote.repository

import com.devtamuno.cashipayment.shared.util.Resource
import com.devtamuno.cashipayment.data.remote.data.PaymentRemote
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
  suspend fun initiateTransaction(payment: PaymentRemote): Resource<Unit>

  fun observeTransactions(): Flow<List<PaymentRemote>>
}
