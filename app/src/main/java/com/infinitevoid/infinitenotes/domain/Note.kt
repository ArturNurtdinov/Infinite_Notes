package com.infinitevoid.infinitenotes.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
    var noteID: Long = 0L,

    var timeEdit: Long = System.currentTimeMillis(),

    var title: String = "Title",

    var content: String = "Content",

    var imageURI: String? = null
) : Parcelable