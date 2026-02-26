package com.devtamuno.cashipayment.presentation.viewmodel

import app.cash.turbine.test
import com.devtamuno.cashipayment.domain.model.Payment
import com.devtamuno.cashipayment.domain.usecase.GetAllPaymentUseCase
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class PaymentViewModelTest :
    BehaviorSpec({
      val testDispatcher = StandardTestDispatcher()
      val getAllPaymentUseCase: GetAllPaymentUseCase = mock()

      lateinit var viewModel: PaymentViewModel
      lateinit var paymentsFlow: MutableStateFlow<List<Payment>>

      beforeTest {
        Dispatchers.setMain(testDispatcher)
        paymentsFlow = MutableStateFlow(emptyList())
        every { getAllPaymentUseCase() } returns paymentsFlow

        viewModel = PaymentViewModel(getAllPaymentUseCase)
      }

      afterTest { Dispatchers.resetMain() }

      Given("PaymentViewModel") {
        Then("Initial state: payments should be empty and sheet should be hidden") {
          viewModel.isSheetVisible.value shouldBe false
          viewModel.payments.value shouldBe emptyList()
        }

        When("the use case emits new data") {
          Then("payments should update reactively") {
            runTest {
              val mockPayments = listOf(Payment("user@test.com", 50.0, "USD", "SUCCESS", 123456L))

              viewModel.payments.test {
                awaitItem() shouldBe emptyList()

                paymentsFlow.value = mockPayments
                awaitItem() shouldBe mockPayments
              }
            }
          }
        }

        When("showPaymentSheet() is called") {
          Then("isSheetVisible should be set to true") {
            runTest {
              viewModel.isSheetVisible.test {
                awaitItem() shouldBe false

                viewModel.showPaymentSheet()
                awaitItem() shouldBe true
              }
            }
          }
        }

        When("hidePaymentSheet() is called") {
          Then("isSheetVisible should be set to false") {
            runTest {
              viewModel.showPaymentSheet()

              viewModel.isSheetVisible.test {
                awaitItem() shouldBe true

                viewModel.hidePaymentSheet()
                awaitItem() shouldBe false
              }
            }
          }
        }
      }
    })
