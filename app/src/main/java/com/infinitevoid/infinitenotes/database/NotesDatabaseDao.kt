package com.infinitevoid.infinitenotes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDatabaseDao {
    @Insert
    fun insert(note: NoteRest)

    @Update
    fun update(note: NoteRest)

    @Query("DELETE FROM notes_table WHERE noteID = :key")
    fun delete(key: Long)

    @Query("SELECT * FROM notes_table WHERE noteID = :key")
    fun get(key: Long): NoteRest

    @Query("SELECT * FROM notes_table ORDER BY time_edit_millis DESC")
    fun getAllNotes(): LiveData<List<NoteRest>>
}