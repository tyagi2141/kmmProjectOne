package com.example.myfirstkmmapp.domain.note

import com.example.myfirstkmmapp.domain.time.DateTimeUtil

class SearchNote {

    fun execute(notes: List<Note>, query: String): List<Note> {
        if (query.isBlank()) {
            return notes
        }
        return notes.filter {
            it.title.trim().lowercase().contains(query) || it.content.trim().lowercase()
                .contains(query)
        }.sortedBy { DateTimeUtil.toEpochMillis(it.created) }
    }
}