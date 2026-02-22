package com.example.myapplication.domain

data class Todo(
    val id: Int = 0, val taskDescription: String,
    val isCompleted: Boolean = false, val eta: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val priority: Priority = Priority.LOW
)