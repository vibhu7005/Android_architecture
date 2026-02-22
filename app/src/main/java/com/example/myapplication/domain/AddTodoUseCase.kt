package com.example.myapplication.domain

import javax.inject.Inject

class AddTodoUseCase @Inject constructor(val repo: TodoRepository) {
    suspend operator fun invoke(todo: Todo): Result<Unit> {
        if (!todo.isCompleted && System.currentTimeMillis() < todo.eta) {
            repo.addTodo(todo)
            return Result.success(Unit)
        } else {
            return Result.failure(IllegalArgumentException("Todo must not be completed and ETA must be in the future"))
        }
    }
}