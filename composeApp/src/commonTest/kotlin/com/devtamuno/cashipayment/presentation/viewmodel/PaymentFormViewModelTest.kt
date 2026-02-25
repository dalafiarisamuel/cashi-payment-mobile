package com.devtamuno.cashipayment.presentation.viewmodel

import app.cash.turbine.test
import com.devtamuno.cashipayment.domain.model.Currency
import com.devtamuno.cashipayment.domain.model.Payment
import com.devtamuno.cashipayment.domain.usecase.InitiateTransactionUseCase
import com.devtamuno.cashipayment.presentation.state.InitiateTransactionState
import com.devtamuno.cashipayment.shared.util.Resource
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class PaymentFormViewModelTest : FunSpec({

    lateinit var viewModel: PaymentFormViewModel
    val initiateTransactionUseCase: InitiateTransactionUseCase = mock()
    val testDispatcher = StandardTestDispatcher()

    beforeTest {
        Dispatchers.setMain(testDispatcher)
        viewModel = PaymentFormViewModel(initiateTransactionUseCase)
    }

    afterTest {
        Dispatchers.resetMain()
    }

    test("Initial state should be correct") {
        viewModel.email shouldBe ""
        viewModel.amount shouldBe ""
        viewModel.currency shouldBe Currency.SUPPORTED_CURRENCIES.first()
        viewModel.isFormValid shouldBe false
        viewModel.transactionState.value shouldBe InitiateTransactionState.Idle
    }

    test("updateEmail should update the email state") {
        val newEmail = "test@example.com"
        viewModel.updateEmail(newEmail)
        viewModel.email shouldBe newEmail
    }

    test("updateAmount should update the amount state") {
        val newAmount = "123.45"
        viewModel.updateAmount(newAmount)
        viewModel.amount shouldBe newAmount
    }

    test("updateCurrency should update the currency state") {
        val newCurrency = "EUR"
        viewModel.updateCurrency(newCurrency)
        viewModel.currency shouldBe newCurrency
    }

    test("isFormValid should be true for valid inputs") {
        viewModel.updateEmail("valid@email.com")
        viewModel.updateAmount("100")
        viewModel.isFormValid shouldBe true
    }

    test("isFormValid should be false for invalid email") {
        viewModel.updateEmail("invalid-email")
        viewModel.updateAmount("100")
        viewModel.isFormValid shouldBe false
    }

    test("isFormValid should be false for zero amount") {
        viewModel.updateEmail("valid@email.com")
        viewModel.updateAmount("0")
        viewModel.isFormValid shouldBe false
    }

    test("resetForm should reset all form states") {
        viewModel.updateEmail("test@example.com")
        viewModel.updateAmount("100")
        viewModel.updateCurrency("EUR")

        viewModel.resetForm()

        viewModel.email shouldBe ""
        viewModel.amount shouldBe ""
        viewModel.currency shouldBe Currency.SUPPORTED_CURRENCIES.first()
        viewModel.transactionState.value shouldBe InitiateTransactionState.Idle
    }

    test("initiateTransaction should emit Loading then Success on successful use case execution") {
        runTest {
            val payment = Payment("test@example.com", 100.0, "USD", "PENDING", 0L)
            everySuspend { initiateTransactionUseCase(any()) } returns Resource.Success(Unit)

            viewModel.updateEmail(payment.recipientEmail)
            viewModel.updateAmount(payment.amount.toString())
            viewModel.updateCurrency(payment.currency)

            viewModel.transactionState.test {
                awaitItem() shouldBe InitiateTransactionState.Idle
                viewModel.initiateTransaction()
                awaitItem() shouldBe InitiateTransactionState.Loading
                awaitItem() shouldBe InitiateTransactionState.Success
            }
        }
    }

    test("initiateTransaction should emit Loading then Failure on failed use case execution") {
        runTest {
            val errorMessage = "Transaction Failed"
            everySuspend { initiateTransactionUseCase(any()) } returns Resource.Failure(errorMessage)

            viewModel.updateEmail("test@example.com")
            viewModel.updateAmount("100")

            viewModel.transactionState.test {
                awaitItem() shouldBe InitiateTransactionState.Idle
                viewModel.initiateTransaction()
                awaitItem() shouldBe InitiateTransactionState.Loading
                val finalState = awaitItem()
                finalState.shouldBeInstanceOf<InitiateTransactionState.Failure>()
                (finalState as InitiateTransactionState.Failure).message shouldBe errorMessage
            }
        }
    }
})
