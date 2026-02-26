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
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class PaymentFormViewModelTest :
    BehaviorSpec({
      val initiateTransactionUseCase: InitiateTransactionUseCase = mock()
      val testDispatcher = StandardTestDispatcher()
      lateinit var viewModel: PaymentFormViewModel

      beforeTest {
        Dispatchers.setMain(testDispatcher)
        viewModel = PaymentFormViewModel(initiateTransactionUseCase)
      }

      afterTest { Dispatchers.resetMain() }

      Given("PaymentFormViewModel") {
        Then("Initial state should be correct") {
          viewModel.email shouldBe ""
          viewModel.amount shouldBe ""
          viewModel.currency shouldBe Currency.SUPPORTED_CURRENCIES.first()
          viewModel.isFormValid shouldBe false
          viewModel.transactionState.value shouldBe InitiateTransactionState.Idle
        }

        When("updateEmail is called") {
          val newEmail = "test@example.com"
          Then("it should update the email state") {
            viewModel.updateEmail(newEmail)
            viewModel.email shouldBe newEmail
          }
        }

        When("updateAmount is called") {
          val newAmount = "123.45"
          Then("it should update the amount state") {
            viewModel.updateAmount(newAmount)
            viewModel.amount shouldBe newAmount
          }
        }

        When("updateCurrency is called") {
          val newCurrency = "EUR"
          Then("it should update the currency state") {
            viewModel.updateCurrency(newCurrency)
            viewModel.currency shouldBe newCurrency
          }
        }

        When("validating form with inputs") {
          Then("isFormValid should be true for valid inputs") {
            viewModel.updateEmail("valid@email.com")
            viewModel.updateAmount("100")
            viewModel.isFormValid shouldBe true
          }

          Then("isFormValid should be false for invalid email") {
            viewModel.updateEmail("invalid-email")
            viewModel.updateAmount("100")
            viewModel.isFormValid shouldBe false
          }

          Then("isFormValid should be false for zero amount") {
            viewModel.updateEmail("valid@email.com")
            viewModel.updateAmount("0")
            viewModel.isFormValid shouldBe false
          }
        }

        When("resetForm is called") {
          Then("it should reset all form states") {
            viewModel.updateEmail("test@example.com")
            viewModel.updateAmount("100")
            viewModel.updateCurrency("EUR")

            viewModel.resetForm()

            viewModel.email shouldBe ""
            viewModel.amount shouldBe ""
            viewModel.currency shouldBe Currency.SUPPORTED_CURRENCIES.first()
            viewModel.transactionState.value shouldBe InitiateTransactionState.Idle
          }
        }

        When("initiateTransaction is called") {
          And("use case execution is successful") {
            Then("it should emit Loading then Success") {
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
          }

          And("use case execution fails") {
            Then("it should emit Loading then Failure") {
              runTest {
                val errorMessage = "Transaction Failed"
                everySuspend { initiateTransactionUseCase(any()) } returns
                    Resource.Failure(errorMessage)

                viewModel.updateEmail("test@example.com")
                viewModel.updateAmount("100")

                viewModel.transactionState.test {
                  awaitItem() shouldBe InitiateTransactionState.Idle
                  viewModel.initiateTransaction()
                  awaitItem() shouldBe InitiateTransactionState.Loading
                  val finalState = awaitItem()
                  finalState.shouldBeInstanceOf<InitiateTransactionState.Failure>()
                  finalState.message shouldBe errorMessage
                }
              }
            }
          }
        }
      }
    })
