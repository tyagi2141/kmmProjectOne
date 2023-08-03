package com.example.myfirstkmmapp.android.node_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstkmmapp.android.di.Constant
import com.example.myfirstkmmapp.android.di.Constant.IsSearchActive
import com.example.myfirstkmmapp.android.di.Constant.Search
import com.example.myfirstkmmapp.domain.NoteDataSource
import com.example.myfirstkmmapp.domain.note.Note
import com.example.myfirstkmmapp.domain.note.SearchNote
import com.example.myfirstkmmapp.domain.time.DateTimeUtil
import com.example.myfirstkmmapp.presentation.RedOrangeHex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NodeListViewModel @Inject constructor(
    private val dataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchNote = SearchNote()
    private val note = savedStateHandle.getStateFlow(Constant.Note, emptyList<Note>())
    private val searchText = savedStateHandle.getStateFlow(Search, "")
    private val isSearchActive = savedStateHandle.getStateFlow(IsSearchActive, false)

    val state = combine(note, searchText, isSearchActive) { note, searchText, isSearchActive ->

        NodeListState(
            note = searchNote.execute(note, searchText),
            searchText = searchText,
            searchState = isSearchActive
        )

    }.stateIn(scope = viewModelScope,   SharingStarted.WhileSubscribed(5000),NodeListState())


   /* init {
        viewModelScope.launch {
             (1..10).forEach{
                 dataSource.insertNote(Note(
                     id = null,
                     title = "title $it",
                     content = "content $it",
                     colorHex = RedOrangeHex,
                     created = DateTimeUtil.now()
                 ))
        }

        }
    }*/

    fun loadNote(){
        viewModelScope.launch { savedStateHandle[Constant.Note] = dataSource.getAllNotes() }
    }

 /*   fun onSearchText(text:String){
        viewModelScope.launch { savedStateHandle["search"] = text }
    }*/

    fun onSearchTextChange(text: String) {
        savedStateHandle[Search] = text
    }
    fun onToggleSearch(){
        viewModelScope.launch { savedStateHandle[IsSearchActive] = !isSearchActive.value }
        if (!isSearchActive.value){
            savedStateHandle[Search] = ""
        }
    }


    fun deleteNodeById(id:Long){
        viewModelScope.launch {
            dataSource.deleteById(id)
            loadNote()
        }
    }
}