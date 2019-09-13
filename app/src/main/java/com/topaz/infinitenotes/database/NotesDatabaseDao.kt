package com.topaz.infinitenotes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDatabaseDao {
    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Query("DELETE FROM notes_table WHERE noteID = :key")
    fun delete(key: Long)

    @Query("SELECT * FROM notes_table WHERE noteID = :key")
    fun get(key: Long): Note

    @Query("SELECT * FROM notes_table ORDER BY time_edit_millis DESC")
    fun getAllNotes(): LiveData<List<Note>>
}