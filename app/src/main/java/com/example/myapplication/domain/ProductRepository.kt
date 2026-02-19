package com.example.myapplication.domain

interface ProductRepository {
    suspend fun fetchProducts() : Result<List<Product>>
}