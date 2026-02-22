package com.example.myapplication.data

import com.example.myapplication.domain.Todo
import com.example.myapplication.domain.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val dao: TodoDao
) : TodoRepository{
    override suspend fun getAllTodos(): Flow<List<Todo>> =
        dao.getAllTodos().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun addTodo(todo: Todo) {
        dao.insertTodo(todo.toEntity())
    }

    override suspend fun deleteTodo(todo: Todo) {
        dao.deleteTodo(todo.toEntity())
    }

    override suspend fun editCompletedStatus(id: Int, isCompleted: Boolean) {
        val todoEntity = dao.getTodoById(id)
        val updatedTodo = todoEntity.copy(isCompleted = isCompleted)
        dao.updateTodo(updatedTodo)
    }

    override suspend fun getCompletionStatus(id: Int): Boolean {
        return dao.getTodoById(id.toInt()).isCompleted
    }
}
