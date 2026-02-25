@file:OptIn(ExperimentalTime::class)

package com.devtamuno.cashipayment.domain.model

import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Payment(
    val recipientEmail: String,
    val amount: Double,
    val currency: String,
    val status: String,
    val timestamp: Long = 0L,
) {
  fun isValid(): Boolean {
    // basic email validation
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
    return emailRegex.matches(recipientEmail) && amount > 0
  }
}

fun Payment.formatTimestamp(): String {
  if (timestamp <= 0L) return "Just now"

  return try {
    val instant = Instant.fromEpochMilliseconds(timestamp)
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val day = dateTime.dayOfMonth
    val monthName = dateTime.month.name.lowercase().replaceFirstChar { it.uppercase() }
    val year = dateTime.year

    val hour = dateTime.hour.toString().padStart(2, '0')
    val minute = dateTime.minute.toString().padStart(2, '0')

    "$day $monthName, $year • $hour:$minute"
  } catch (e: Exception) {
    "Just now"
  }
}
