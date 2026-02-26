package com.example.myapplication.domain

import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val expenseRepo: ExpenseRepo
) {
    suspend operator fun invoke(expenseName: String, method: String, amount: Double): Result<Unit> {
        if (expenseName.isEmpty()) {
            return Result.failure(IllegalArgumentException("Expense name cannot be empty"))
        }
        if (method.isEmpty()) {
            return Result.failure(IllegalArgumentException("Payment method cannot be empty"))
        }
        if (amount <= 0) {
            return Result.failure(IllegalArgumentException("Amount must be greater than 0"))
        }
        
        val expense = Expense(
            expenseName = expenseName,
            method = method,
            amount = amount
        )
        
        expenseRepo.addExpense(expense)
        return Result.success(Unit)
    }
}