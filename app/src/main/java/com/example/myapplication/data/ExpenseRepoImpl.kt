package com.example.myapplication.data

import com.example.myapplication.domain.Expense
import com.example.myapplication.domain.ExpenseRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseRepoImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpenseRepo {

    override suspend fun addExpense(expense: Expense) {
        expenseDao.insertExpense(expense.toExpenseEntity())
    }

    override suspend fun updateExpense(expense: Expense) {
        expenseDao.updateExpense(expense.toExpenseEntity())
    }

    override suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense.toExpenseEntity())
    }

    override suspend fun getExpenseById(id: Int): Expense? {
        return expenseDao.getExpenseById(id)?.toExpense()
    }

    override suspend fun deleteAllExpenses() {
        expenseDao.deleteAllExpenses()
    }

    override fun observeExpenses(): Flow<List<Expense>> {
        return expenseDao.getAllExpenses().map { entities ->
            entities.map { it.toExpense() }
        }
    }
}

fun Expense.toExpenseEntity(): ExpenseEntity {
    return ExpenseEntity(
        expenseId = expenseId,
        expenseName = expenseName,
        method = method,
        amount = amount
    )
}

fun ExpenseEntity.toExpense(): Expense {
    return Expense(
        expenseId = expenseId,
        expenseName = expenseName,
        method = method,
        amount = amount
    )
}