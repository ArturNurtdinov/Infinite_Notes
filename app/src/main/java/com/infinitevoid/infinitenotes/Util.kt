package com.infinitevoid.infinitenotes

import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToDate(timestamp: Long): String {
    val date = Date(timestamp)
    val dateFormat =
        if (date.day == Date(System.currentTimeMillis()).day) {
            SimpleDateFormat("HH:mm", Locale.getDefault())
        } else {
            SimpleDateFormat("MMM d", Locale.getDefault())
        }
    return dateFormat.format(date)
}