package com.example.myfirstkmmapp.android.node_list

import com.example.myfirstkmmapp.domain.note.Note

data class NodeListState(
    val note: List<Note> = emptyList(),
    val searchText: String = "",
    val searchState: Boolean = false
)