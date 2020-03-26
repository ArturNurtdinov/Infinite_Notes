package com.infinitevoid.infinitenotes.newnotes

import androidx.lifecycle.ViewModel
import com.infinitevoid.infinitenotes.database.NoteRest
import com.infinitevoid.infinitenotes.database.NotesDatabaseDao
import com.infinitevoid.infinitenotes.models.Note
import com.infinitevoid.infinitenotes.mappers.mapNoteToNoteRest
import kotlinx.coroutines.*

class NewnoteViewModel(private val dataSource: NotesDatabaseDao) : ViewModel() {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    val notes = dataSource.getAllNotes()

    private suspend fun insert(note: NoteRest) {
        withContext(Dispatchers.IO) {
            dataSource.insert(note)
        }
    }

    private suspend fun update(note: NoteRest) {
        withContext(Dispatchers.IO) {
            dataSource.update(note)
        }
    }

    fun insertNote(note: Note) {
        uiScope.launch { insert(mapNoteToNoteRest(note)) }
    }

    fun updateNote(note: Note) {
        uiScope.launch { update(mapNoteToNoteRest(note)) }
    }
}