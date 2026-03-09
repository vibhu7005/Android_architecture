package com.example.myapplication.presenter.uiState

import com.example.core.domain.model.Product

sealed class ProductUiState {
    data class Success(val products: List<Product>) : ProductUiState()
    data object Empty : ProductUiState()
    data class Error(val message: String) : ProductUiState()
    data object Loading : ProductUiState()
}