package com.example.myapplication.domain

interface ProductRepository {
    suspend fun fetchProducts() : Result<List<Product>>
    suspend fun filterProducts(query : String) : Result<List<Product>>

}