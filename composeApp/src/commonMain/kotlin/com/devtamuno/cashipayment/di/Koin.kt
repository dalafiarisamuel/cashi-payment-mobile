package com.devtamuno.cashipayment.di

import com.devtamuno.cashipayment.domain.usecase.GetAllPaymentUseCase
import com.devtamuno.cashipayment.domain.usecase.InitiateTransactionUseCase
import com.devtamuno.cashipayment.data.remote.api.PaymentApi
import com.devtamuno.cashipayment.data.remote.api.createPaymentApi
import com.devtamuno.cashipayment.presentation.viewmodel.PaymentFormViewModel
import com.devtamuno.cashipayment.presentation.viewmodel.PaymentViewModel
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
  appDeclaration()
  modules(
      networkModule(),
      platformModule(),
      viewModelModule(),
      useCaseModule(),
  )
}

fun initKoin() = initKoin() {}

private fun networkModule() = module {
  single {
    HttpClient {
      install(ContentNegotiation) {
        json(
            Json {
              ignoreUnknownKeys = true
              prettyPrint = true
              isLenient = true
            }
        )
      }
      install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
      }
      defaultRequest {
          contentType(ContentType.Application.Json)
      }
    }
  }

  single<Ktorfit> {
    Ktorfit.Builder().build {
      httpClient(get<HttpClient>())
      baseUrl("http://10.0.2.2:3604/")
    }
  }
  single<PaymentApi> { get<Ktorfit>().createPaymentApi() }
}

private fun useCaseModule() = module {
  singleOf(::GetAllPaymentUseCase)
  singleOf(::InitiateTransactionUseCase)
}

private fun viewModelModule() = module {
    viewModelOf(::PaymentViewModel)
    viewModelOf(::PaymentFormViewModel)
}
