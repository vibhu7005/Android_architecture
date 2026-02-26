package com.example.myapplication.domain

import javax.inject.Inject

class NotesCountUseCase @Inject constructor(val repo: NotesRepo) {
    suspend operator fun invoke()  = repo.getNotesCount()
}