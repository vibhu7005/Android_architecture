package com.example.myapplication.domain

class FetchProductsUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke() = repository.fetchProducts()
}