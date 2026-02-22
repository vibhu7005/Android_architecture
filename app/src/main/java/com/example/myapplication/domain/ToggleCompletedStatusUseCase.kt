package com.example.myapplication.domain

import javax.inject.Inject

class ToggleCompletedStatusUseCase @Inject constructor(val repository: TodoRepository) {
    suspend operator fun invoke(id: Int, isCompleted: Boolean): Result<Unit> {
        repository.editCompletedStatus(id, !isCompleted)
        return Result.success(Unit)
    }
}