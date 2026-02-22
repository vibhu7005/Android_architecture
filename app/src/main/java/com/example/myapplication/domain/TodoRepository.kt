package com.example.myapplication.domain

import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun getAllTodos(): Flow<List<Todo>>
    suspend fun addTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
    suspend fun editCompletedStatus(id: Int, isCompleted: Boolean)
    suspend fun getCompletionStatus(id: Int): Boolean
}