package com.infinitevoid.infinitenotes.notes

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.infinitevoid.infinitenotes.convertTimestampToDate
import com.infinitevoid.infinitenotes.models.Note

@BindingAdapter("timestampToDate")
fun TextView.setTimestampToDate(item: Note) {
    text = convertTimestampToDate(item.timeEdit)
}