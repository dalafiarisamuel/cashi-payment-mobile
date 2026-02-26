package com.devtamuno.cashipayment.domain.usecase

import com.devtamuno.cashipayment.data.remote.repository.PaymentRepository
import com.devtamuno.cashipayment.domain.model.Payment
import com.devtamuno.cashipayment.shared.util.Resource
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest

class InitiateTransactionUseCaseTest :
    BehaviorSpec({
      val repository: PaymentRepository = mock()
      val useCase = InitiateTransactionUseCase(repository)

      Given("InitiateTransactionUseCase") {
        val payment = Payment("test@example.com", 100.0, "USD", "PENDING", 0L)

        When("invoked and repository call is successful") {
          Then("it should return Success") {
            runTest {
              everySuspend { repository.initiateTransaction(any()) } returns Resource.Success(Unit)

              val result = useCase(payment)

              result shouldBe Resource.Success(Unit)
            }
          }
        }

        When("invoked and repository call fails") {
          Then("it should return Failure") {
            runTest {
              val errorMessage = "Network Error"
              everySuspend { repository.initiateTransaction(any()) } returns
                  Resource.Failure(errorMessage)

              val result = useCase(payment)

              result shouldBe Resource.Failure(errorMessage)
            }
          }
        }
      }
    })
