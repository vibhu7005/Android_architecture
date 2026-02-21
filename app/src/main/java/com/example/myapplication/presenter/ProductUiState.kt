package com.example.myapplication.presenter

import com.example.myapplication.domain.Product

sealed class ProductUiState {
    data class Success(val products : List<Product>) : ProductUiState()
    data object Loading : ProductUiState()

    data object Idle : ProductUiState()
}