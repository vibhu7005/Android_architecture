package com.example.myapplication.presenter

import domain.models.Product

sealed interface ProductUiState {
    data class Success(val productsList : List<Product>) : ProductUiState
    data object Empty : ProductUiState
    data object Loading : ProductUiState
    data class Error(val message : String) : ProductUiState
}