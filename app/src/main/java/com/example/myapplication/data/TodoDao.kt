package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY priority DESC, createdAt DESC")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(entity: TodoEntity): Long

    @Update
    suspend fun updateTodo(entity: TodoEntity)

    @Delete
    suspend fun deleteTodo(entity: TodoEntity)

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id : Int): TodoEntity

    @Query("SELECT * FROM todos WHERE isSynced = 0")
    suspend fun getUnsyncedTodos(): List<TodoEntity>

    @Query("UPDATE todos SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<Long>)
}