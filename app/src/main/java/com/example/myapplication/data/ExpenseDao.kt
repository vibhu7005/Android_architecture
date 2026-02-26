package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    
    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)
    
    @Update
    suspend fun updateExpense(expense: ExpenseEntity)
    
    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)
    
    @Query("SELECT * FROM expense_table ORDER BY expenseId DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
    
    @Query("SELECT * FROM expense_table WHERE expenseId = :id")
    suspend fun getExpenseById(id: Int): ExpenseEntity?
    
    @Query("DELETE FROM expense_table")
    suspend fun deleteAllExpenses()
}