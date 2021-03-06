package com.infinitevoid.infinitenotes.notes

import androidx.lifecycle.ViewModel
import com.infinitevoid.infinitenotes.database.NoteRest
import com.infinitevoid.infinitenotes.database.NotesDatabaseDao
import kotlinx.coroutines.*

class NotesViewModel(
    private val dataSource: NotesDatabaseDao
) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val notes = dataSource.getAllNotes()

    private suspend fun insert(note: NoteRest) {
        withContext(Dispatchers.IO) {
            dataSource.insert(note)
        }
    }

    private suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            dataSource.delete(id)
        }
    }

    fun insertNote(note: NoteRest) {
        uiScope.launch { insert(note) }
    }

    fun deleteNote(id: Long) {
        uiScope.launch { delete(id) }
    }
}