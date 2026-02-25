package com.devtamuno.cashipayment.shared.util

sealed class Resource<out R> {
    data class Success<out R>(val result: R) : Resource<R>()
    data class Failure(val error: String) : Resource<Nothing>()
}

internal suspend fun <R> resourceHelper(body: suspend () -> R): Resource<R> {
    return try {
        Resource.Success(body())
    } catch (e: Exception) {
        Resource.Failure(e.message ?: "Something went wrong")
    }
}
