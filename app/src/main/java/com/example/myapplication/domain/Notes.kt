package com.example.myapplication.domain

data class Notes(
    val id: Int = 0,
    val title: String,
    val description: String,
    val time: Long = System.currentTimeMillis()
)

