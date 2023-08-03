package com.example.myfirstkmmapp.domain.note

import com.example.myfirstkmmapp.database.NoteDatabase
import com.example.myfirstkmmapp.domain.NoteDataSource
import com.example.myfirstkmmapp.domain.time.DateTimeUtil

class sqlDelightDataSource(val db: NoteDatabase) : NoteDataSource {

    val queries = db.noteQueries;
    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            note.id, note.title, note.content, note.colorHex,
            DateTimeUtil.toEpochMillis(note.created)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries.getNoteById(id).executeAsOneOrNull()?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries.getAllNote().executeAsList().map { it.toNote() }
    }

    override suspend fun deleteById(id: Long) {
        queries.deletNoteById(id)
    }
}