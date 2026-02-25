package com.devtamuno.cashipayment.data.remote.repository

import com.devtamuno.cashipayment.data.remote.api.PaymentApi
import com.devtamuno.cashipayment.data.remote.data.PaymentRemote
import com.devtamuno.cashipayment.shared.util.Resource
import com.devtamuno.cashipayment.shared.util.resourceHelper
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal actual class PaymentRepositoryImpl(
    firestore: FirebaseFirestore,
    private val paymentApi: PaymentApi,
) : PaymentRepository {
  private val paymentsCollection = firestore.collection("cashi-payment-database")

  actual override suspend fun initiateTransaction(payment: PaymentRemote): Resource<Unit> {

    return resourceHelper {
        val response = paymentApi.processPayment(payment)
        if (!response.success) {
            throw Exception(response.message)
        }
    }
  }

  actual override fun observeTransactions(): Flow<List<PaymentRemote>> {
    return paymentsCollection.orderBy("timestamp", Query.Direction.DESCENDING).snapshots().map {
        querySnapshot ->
      querySnapshot.documents.mapNotNull { it.toPaymentRemote() }
    }
  }

  private fun DocumentSnapshot.toPaymentRemote(): PaymentRemote? {
    if (!exists()) return null
    return PaymentRemote(
        recipientEmail = getString("recipientEmail"),
        amount = getDouble("amount"),
        currency = getString("currency"),
        status = getString("status"),
        timestamp = getTimestampAsLong("timestamp"),
    )
  }

  private fun DocumentSnapshot.getTimestampAsLong(field: String): Long? {
    return when (val value = get(field)) {
      is com.google.firebase.Timestamp -> value.toDate().time
      is Number -> value.toLong()
      else -> null
    }
  }
}
