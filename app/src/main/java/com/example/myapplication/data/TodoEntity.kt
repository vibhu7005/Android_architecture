package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.domain.Priority

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val eta: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val priority: Priority = Priority.LOW,
    val isSynced : Boolean = false
)