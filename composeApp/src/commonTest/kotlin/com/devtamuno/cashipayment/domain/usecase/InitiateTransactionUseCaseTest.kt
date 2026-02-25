package com.devtamuno.cashipayment.domain.usecase

import com.devtamuno.cashipayment.data.remote.data.toRemote
import com.devtamuno.cashipayment.data.remote.repository.PaymentRepository
import com.devtamuno.cashipayment.domain.model.Payment
import com.devtamuno.cashipayment.shared.util.Resource
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest

class InitiateTransactionUseCaseTest : FunSpec({

    val repository: PaymentRepository = mock()
    val useCase = InitiateTransactionUseCase(repository)

    test("invoke should return Success when repository call is successful") {
        runTest {
            val payment = Payment("test@example.com", 100.0, "USD", "PENDING", 0L)
            everySuspend { repository.initiateTransaction(any()) } returns Resource.Success(Unit)

            val result = useCase(payment)

            result shouldBe Resource.Success(Unit)
        }
    }

    test("invoke should return Failure when repository call fails") {
        runTest {
            val payment = Payment("test@example.com", 100.0, "USD", "PENDING", 0L)
            val errorMessage = "Network Error"
            everySuspend { repository.initiateTransaction(any()) } returns Resource.Failure(errorMessage)

            val result = useCase(payment)

            result shouldBe Resource.Failure(errorMessage)
        }
    }
})
