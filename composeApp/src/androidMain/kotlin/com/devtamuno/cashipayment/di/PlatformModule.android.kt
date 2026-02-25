package com.devtamuno.cashipayment.di

import com.devtamuno.cashipayment.data.remote.repository.PaymentRepository
import com.devtamuno.cashipayment.data.remote.repository.PaymentRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import org.koin.core.module.Module
import org.koin.dsl.module

internal actual fun platformModule(): Module {
  return module {
    single<FirebaseFirestore> { Firebase.firestore }
    single<PaymentRepository> { PaymentRepositoryImpl(get(), get()) }
  }
}
