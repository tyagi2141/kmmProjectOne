package com.example.myfirstkmmapp.android.note_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstkmmapp.android.di.Constant
import com.example.myfirstkmmapp.android.di.Constant.NoteColor
import com.example.myfirstkmmapp.android.di.Constant.NoteContent
import com.example.myfirstkmmapp.android.di.Constant.NoteContentFocus
import com.example.myfirstkmmapp.android.di.Constant.NoteId
import com.example.myfirstkmmapp.android.di.Constant.NoteTile
import com.example.myfirstkmmapp.android.di.Constant.NoteTitleFocus
import com.example.myfirstkmmapp.domain.NoteDataSource
import com.example.myfirstkmmapp.domain.note.Note
import com.example.myfirstkmmapp.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    val noteDataSource: NoteDataSource,
    val saveState: SavedStateHandle
) : ViewModel() {

    val noteTitle = saveState.getStateFlow(Constant.NoteTile, "")
    val isNoteTitleFocus = saveState.getStateFlow(NoteTitleFocus, false)
    val noteContent = saveState.getStateFlow(NoteContent, "")
    val isNoteContentFocus = saveState.getStateFlow(NoteContentFocus, false)
    val noteColor = saveState.getStateFlow(NoteColor, Note.generateRandomColor())

    val state = combine(
        noteTitle,
        isNoteTitleFocus,
        noteContent,
        isNoteContentFocus,
        noteColor
    ) { title, isTitleFocus, content, isContentFocus, color ->
        NoteDetailState(
            noteTitle = title,
            isNoteTitleHintVisible = title.isEmpty() && !isTitleFocus,
            noteContent = content,
            isNoteContentHintVisible = content.isEmpty() && !isContentFocus,
            noteColor = color

        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private val _hasNoteBeenSave = MutableStateFlow(false)
    val hasNoteBeenSave = _hasNoteBeenSave.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        saveState.get<Long>(NoteId)?.let { exitNoteId ->

            if (exitNoteId == -1L) {
                return@let
            }
            this.existingNoteId = exitNoteId
            viewModelScope.launch {
                noteDataSource.getNoteById(exitNoteId)?.let { note ->
                    saveState[NoteTile] = note.title
                    saveState[NoteContent] = note.content
                    saveState[NoteColor] = note.colorHex
                }
            }
        }
    }


    fun onTitleChange(title: String) {
        saveState[NoteTile] = title
    }

    fun onContentChange(content: String) {
        saveState[NoteContent] = content
    }

    fun isTitleFocus(status: Boolean) {
        saveState[NoteTitleFocus] = status
    }

    fun isContentFocus(status: Boolean) {
        saveState[NoteContentFocus] = status
    }


    fun saveNote() {
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    created = DateTimeUtil.now()
                )
            )
            _hasNoteBeenSave.value = true
        }
    }
}