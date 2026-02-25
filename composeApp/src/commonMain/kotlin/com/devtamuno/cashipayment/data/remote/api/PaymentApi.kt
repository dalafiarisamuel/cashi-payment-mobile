package com.devtamuno.cashipayment.data.remote.api

import com.devtamuno.cashipayment.data.remote.data.PaymentRemote
import com.devtamuno.cashipayment.data.remote.data.PaymentResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.POST

interface PaymentApi {

    @POST("payments")
    suspend fun processPayment(
        @Body payment: PaymentRemote
    ): PaymentResponse
}
