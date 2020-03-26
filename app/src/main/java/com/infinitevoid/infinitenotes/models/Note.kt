package com.infinitevoid.infinitenotes.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
    var noteID: Long = 0L,

    var timeEdit: Long = System.currentTimeMillis(),

    var title: String = "Title",

    var content: String = "Content"
) : Parcelable