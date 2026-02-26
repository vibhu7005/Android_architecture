package com.example.myapplication.domain

import kotlinx.coroutines.flow.Flow

interface NotesRepo {
    suspend fun addNotes(notes : Notes)
    suspend fun deleteNotes(notes : Notes)
    suspend fun getNotesCount() : Int
    fun observeNotes() : Flow<List<Notes>>
}