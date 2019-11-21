package com.infinitevoid.infinitenotes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class NoteRest(
    @PrimaryKey(autoGenerate = true)
    var noteID: Long = 0L,

    @ColumnInfo(name = "time_edit_millis")
    var timeEdit: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "title")
    var title: String = "Title",

    @ColumnInfo(name = "content")
    var content: String = "Content",

    @ColumnInfo(name = "imageURI")
    var imageURI: String? = null
)