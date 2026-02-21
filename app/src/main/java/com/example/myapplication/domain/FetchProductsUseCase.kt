package com.example.myapplication.domain

import javax.inject.Inject

class FetchProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke() = repository.fetchProducts()
}