package com.example.myapplication.domain

import javax.inject.Inject

class AddNotesUseCase @Inject constructor(val repo: NotesRepo) {
    suspend operator fun invoke(title: String, description: String) : Result<Unit> {
        if (title.isEmpty() || description.isEmpty()) {
            return Result.failure(IllegalArgumentException("Title and description cannot be empty"))
        }
        repo.addNotes(Notes(title = "title", description = "description"))
        return Result.success(Unit)

    }
}