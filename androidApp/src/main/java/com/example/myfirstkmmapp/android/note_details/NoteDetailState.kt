package com.example.myfirstkmmapp.android.note_details

data class NoteDetailState(
    val noteTitle: String = "",
    val isNoteTitleHintVisible: Boolean = true,
    val noteContent: String = "",
    val isNoteContentHintVisible: Boolean = true,
    val noteColor: Long = 0xFFFFFF
)