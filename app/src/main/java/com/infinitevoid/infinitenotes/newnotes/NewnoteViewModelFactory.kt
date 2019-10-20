package com.infinitevoid.infinitenotes.newnotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.infinitevoid.infinitenotes.database.NotesDatabaseDao
import java.lang.IllegalArgumentException

class NewnoteViewModelFactory(private val dataSource: NotesDatabaseDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewnoteViewModel::class.java)) {
            return NewnoteViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}