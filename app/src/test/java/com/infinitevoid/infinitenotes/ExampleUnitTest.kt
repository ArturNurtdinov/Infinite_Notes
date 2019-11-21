package com.infinitevoid.infinitenotes

import com.infinitevoid.infinitenotes.database.NoteRest
import com.infinitevoid.infinitenotes.domain.Note
import com.infinitevoid.infinitenotes.mappers.mapNoteRestListToNoteList
import com.infinitevoid.infinitenotes.mappers.mapNoteToNoteRest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun mappingNote() {
        val note = Note(4, 18874032, "Note Title", "Note Content")

        val expected = NoteRest(4, 18874032, "Note Title", "Note Content")

        assertEquals(expected, mapNoteToNoteRest(note))
    }

    @Test
    fun mappingListOfNotes() {
        val note1 = Note(1, 188740321, "Note Title 1", "Note Content 1")
        val note2 = Note(2, 188740322, "Note Title 2", "Note Content 2")
        val note3 = Note(3, 188740323, "Note Title 3", "Note Content 3")

        val expected = listOf(note1, note2, note3)

        val listToMap = listOf(
            mapNoteToNoteRest(note1),
            mapNoteToNoteRest(note2),
            mapNoteToNoteRest(note3)
        )

        assertEquals(expected, mapNoteRestListToNoteList(listToMap))

    }
}
