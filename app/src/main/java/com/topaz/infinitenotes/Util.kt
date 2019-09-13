package com.topaz.infinitenotes

import java.text.SimpleDateFormat
import java.util.*

fun convertTimestampToDate(timestamp: Long): String {
    val date = Date(timestamp * 1000)
    val dateFormat =
        if (date.day == Date(System.currentTimeMillis()).day) {
            SimpleDateFormat("HH:mm", Locale.getDefault())
        } else {
            SimpleDateFormat("MMM d", Locale.getDefault())
        }
    return dateFormat.format(date)
}