package com.devtamuno.cashipayment

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform