package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun getAllTodos(): Flow<List<NotesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(entity: NotesEntity): Long

    @Delete
    suspend fun deleteTodo(entity: NotesEntity)

    @Query("SELECT COUNT(*) FROM notes")
    suspend fun getNotesCount() : Int
}