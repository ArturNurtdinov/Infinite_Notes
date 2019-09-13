package com.topaz.infinitenotes.notes

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.topaz.infinitenotes.convertTimestampToDate
import com.topaz.infinitenotes.database.Note

@BindingAdapter("timestampToDate")
fun TextView.setTimestampToDate(item: Note) {
    text = convertTimestampToDate(item.timeEdit)
}