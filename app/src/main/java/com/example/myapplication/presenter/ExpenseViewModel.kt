package com.example.myapplication.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.AddExpenseUseCase
import com.example.myapplication.domain.Expense
import com.example.myapplication.domain.ExpenseRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseRepo: ExpenseRepo,
    private val addExpenseUseCase: AddExpenseUseCase
) : ViewModel() {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage.asSharedFlow()

    init {
        viewModelScope.launch {
            expenseRepo.observeExpenses().collect { expenseList ->
                _expenses.value = expenseList
            }
        }
        
        // Initialize sample data if database is empty
        initializeSampleDataIfNeeded()
    }
    
    private fun initializeSampleDataIfNeeded() {
        viewModelScope.launch {
            expenseRepo.observeExpenses().take(1).collect { expenses ->
                if (expenses.isEmpty()) {
                    val sampleExpenses = listOf(
                        Triple("Rent", "Bank Transfer", 1200.0),
                        Triple("Insurance", "Credit Card", 149.0),
                        Triple("Groceries", "Wallet", 205.0),
                        Triple("Utilities", "Credit Card", 180.0),
                        Triple("Bill", "Cash", 79.0)
                    )
                    
                    sampleExpenses.forEach { (name, method, amount) ->
                        addExpense(name, method, amount)
                    }
                }
            }
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepo.deleteExpense(expense)
        }
    }

    fun updateExpense(expense: Expense) {
        viewModelScope.launch {
            expenseRepo.updateExpense(expense)
        }
    }

    fun addExpense(expenseName: String, method: String, amount: Double) {
        viewModelScope.launch {
            addExpenseUseCase(expenseName, method, amount).onSuccess {
            }.onFailure { error ->
                _errorMessage.emit(error.message ?: "Failed to add expense")
            }
        }
    }
}