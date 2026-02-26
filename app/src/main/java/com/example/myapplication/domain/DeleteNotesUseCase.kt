package com.example.myapplication.domain

import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(val repo: NotesRepo) {
    suspend operator fun invoke(note: Notes) {
        repo.deleteNotes(note)
    }
}