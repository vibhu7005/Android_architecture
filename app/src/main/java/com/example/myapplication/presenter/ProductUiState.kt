package com.example.myapplication.presenter

import domain.models.Product

data class ProductUiModel(val id: Int, val title: String, val thumbnail: String)

fun Product.toUiModel() = ProductUiModel(id = id, title = title, thumbnail = thumbnail)

sealed interface ProductUiState {
    data class Success(val productsList : List<ProductUiModel>) : ProductUiState
    data object Empty : ProductUiState
    data object Loading : ProductUiState
    data class Error(val message : String) : ProductUiState
}