package com.example.myapplication.data

import com.example.myapplication.domain.Product
import com.example.myapplication.domain.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryImpl() : ProductRepository {
    private val apiService = RetrofitInstance.productApiService
    override suspend fun fetchProducts(): Result<List<Product>> =
        withContext(Dispatchers.IO) {
            runCatching { apiService.getProducts().products.map { it.toProduct()} }
        }
}