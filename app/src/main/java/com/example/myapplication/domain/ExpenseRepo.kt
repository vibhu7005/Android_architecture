package com.example.myapplication.domain

import kotlinx.coroutines.flow.Flow

interface ExpenseRepo {
    suspend fun addExpense(expense: Expense)
    suspend fun updateExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
    suspend fun getExpenseById(id: Int): Expense?
    suspend fun deleteAllExpenses()
    fun observeExpenses(): Flow<List<Expense>>
}