package com.devtamuno.cashipayment.domain.usecase

import app.cash.turbine.test
import com.devtamuno.cashipayment.data.remote.data.PaymentRemote
import com.devtamuno.cashipayment.data.remote.repository.PaymentRepository
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.mock
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

class GetAllPaymentUseCaseTest : FunSpec({

    val repository: PaymentRepository = mock()
    val useCase = GetAllPaymentUseCase(repository)

    test("invoke should return flow of payments from repository") {
        runTest {
            val remotePayments = listOf(
                PaymentRemote(
                    recipientEmail = "test@example.com",
                    amount = 100.0,
                    currency = "USD",
                    status = "COMPLETED",
                    timestamp = 12345L
                )
            )
            every { repository.observeTransactions() } returns flowOf(remotePayments)

            useCase().test {
                val result = awaitItem()
                result.size shouldBe 1
                result[0].recipientEmail shouldBe "test@example.com"
                result[0].amount shouldBe 100.0
                result[0].currency shouldBe "USD"
                result[0].status shouldBe "COMPLETED"
                awaitComplete()
            }
        }
    }

    test("invoke should return empty list when repository emits empty list") {
        runTest {
            every { repository.observeTransactions() } returns flowOf(emptyList())

            useCase().test {
                awaitItem() shouldBe emptyList()
                awaitComplete()
            }
        }
    }
})
