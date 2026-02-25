@file:OptIn(ExperimentalTime::class)

package com.devtamuno.cashipayment.data.remote.data

import com.devtamuno.cashipayment.domain.model.Payment
import kotlin.time.Clock
import kotlinx.serialization.Serializable
import kotlin.time.ExperimentalTime

@Serializable
data class PaymentRemote(
    val recipientEmail: String? = null,
    val amount: Double? = null,
    val currency: String? = null,
    val status: String? = null,
    val timestamp: Long? = null
)

fun Payment.toRemote() = PaymentRemote(
    recipientEmail = recipientEmail,
    amount = amount,
    currency = currency,
    status = status,
    timestamp = Clock.System.now().toEpochMilliseconds()
)

fun PaymentRemote.toDomain() = Payment(
    recipientEmail = recipientEmail ?: "",
    amount = amount ?: 0.0,
    currency = currency ?: "",
    status = status ?: "",
    timestamp = timestamp ?: 0L
)
