package com.devtamuno.cashipayment.data.remote.data

import kotlinx.serialization.Serializable

@Serializable
data class PaymentResponse(
    val success: Boolean,
    val message: String
)
