package com.infinitevoid.infinitenotes

import com.infinitevoid.infinitenotes.domain.Note
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

        assertEquals(note, mapNoteToNoteRest(note))
    }
}
