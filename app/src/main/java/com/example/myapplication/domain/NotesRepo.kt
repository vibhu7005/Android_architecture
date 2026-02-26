package com.example.myapplication.domain

import kotlinx.coroutines.flow.Flow

interface NotesRepo {
    suspend fun addNotes(notes : Notes)
    suspend fun deleteNotes(notes : Notes) : Result<Unit>
    suspend fun getNotesCount() : Result<Int>
    fun observeNotes() : Flow<List<Notes>>
}