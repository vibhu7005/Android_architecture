package com.example.myapplication.domain

data class Expense(
    val expenseId: Int = 0,
    val expenseName: String,
    val method: String,
    val amount: Double
)