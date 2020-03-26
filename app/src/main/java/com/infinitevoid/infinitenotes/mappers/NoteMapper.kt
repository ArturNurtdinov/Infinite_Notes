package com.infinitevoid.infinitenotes.mappers

import com.infinitevoid.infinitenotes.database.NoteRest
import com.infinitevoid.infinitenotes.models.Note

fun mapNoteRestToNote(noteRest: NoteRest): Note {
    with(noteRest) { return Note(noteID, timeEdit, title, content) }
}

fun mapNoteToNoteRest(note: Note): NoteRest {
    with(note) { return NoteRest(noteID, timeEdit, title, content) }
}

fun mapNoteRestListToNoteList(noteRestList: List<NoteRest>): List<Note> {
    return noteRestList.map { mapNoteRestToNote(it) }
}
