package com.example.myapplication.presenter

sealed class ProductIntent {
    data object RefreshProducts : ProductIntent()
    data class SearchProducts(val query : String) : ProductIntent()
}

