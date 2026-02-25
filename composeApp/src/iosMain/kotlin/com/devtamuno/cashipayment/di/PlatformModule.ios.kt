package com.devtamuno.cashipayment.di

import com.devtamuno.cashipayment.data.remote.repository.PaymentRepository
import com.devtamuno.cashipayment.data.remote.repository.PaymentRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule(): Module = module {
    single<PaymentRepository> { PaymentRepositoryImpl() }
}
