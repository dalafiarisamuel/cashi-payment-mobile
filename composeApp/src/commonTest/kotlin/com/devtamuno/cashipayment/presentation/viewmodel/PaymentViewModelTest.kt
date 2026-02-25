package com.devtamuno.cashipayment.presentation.viewmodel

import app.cash.turbine.test
import com.devtamuno.cashipayment.domain.model.Payment
import com.devtamuno.cashipayment.domain.usecase.GetAllPaymentUseCase
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import io.kotest.core.spec.style.FunSpec
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
    FunSpec({
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

        afterTest {
            Dispatchers.resetMain()
        }

        test("Initial state: payments should be empty and sheet should be hidden") {
            viewModel.isSheetVisible.value shouldBe false
            viewModel.payments.value shouldBe emptyList()
        }

        test("payments should update reactively when the use case emits new data") {
            runTest {
                val mockPayments = listOf(
                    Payment("user@test.com", 50.0, "USD", "SUCCESS", 123456L)
                )

                viewModel.payments.test {
                    awaitItem() shouldBe emptyList()

                    paymentsFlow.value = mockPayments
                    awaitItem() shouldBe mockPayments
                }
            }
        }

        test("showPaymentSheet() should set isSheetVisible to true") {
            runTest {
                viewModel.isSheetVisible.test {
                    awaitItem() shouldBe false

                    viewModel.showPaymentSheet()
                    awaitItem() shouldBe true
                }
            }
        }

        test("hidePaymentSheet() should set isSheetVisible to false") {
            runTest {
                viewModel.showPaymentSheet()

                viewModel.isSheetVisible.test {
                    awaitItem() shouldBe true

                    viewModel.hidePaymentSheet()
                    awaitItem() shouldBe false
                }
            }
        }
    })