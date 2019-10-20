package com.infinitevoid.infinitenotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "notes_table")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteID: Long = 0L,

    @ColumnInfo(name = "time_edit_millis")
    var timeEdit: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "title")
    var title: String = "Title",

    @ColumnInfo(name = "content")
    var content: String = "Content"
) : Parcelable