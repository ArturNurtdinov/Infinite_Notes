package com.topaz.infinitenotes.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.topaz.infinitenotes.database.NotesDatabaseDao
import java.lang.IllegalArgumentException

class NotesViewModelFactory(private val dataSource: NotesDatabaseDao):ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            return NotesViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}