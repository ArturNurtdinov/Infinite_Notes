package com.topaz.infinitenotes.newnotes

import androidx.lifecycle.ViewModel
import com.topaz.infinitenotes.database.Note
import com.topaz.infinitenotes.database.NotesDatabaseDao
import kotlinx.coroutines.*

class NewnoteViewModel(private val dataSource: NotesDatabaseDao) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val notes = dataSource.getAllNotes()

    private suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            dataSource.insert(note)
        }
    }

    private suspend fun update(note: Note) {
        withContext(Dispatchers.IO) {
            dataSource.update(note)
        }
    }

    private suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            dataSource.delete(id)
        }
    }

    fun insertNote(note: Note) {
        uiScope.launch { insert(note) }
    }

    fun deleteNote(id: Long) {
        uiScope.launch { delete(id) }
    }

    fun updateNote(note: Note){
        uiScope.launch { update(note) }
    }
}