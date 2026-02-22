package com.example.myapplication.domain

import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(private val repository: TodoRepository) {

    suspend operator fun invoke(todo: Todo): Result<Unit> {
        repository.deleteTodo(todo)
        return Result.success(Unit)
    }
}