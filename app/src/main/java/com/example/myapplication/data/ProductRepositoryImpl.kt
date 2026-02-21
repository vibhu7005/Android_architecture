package com.example.myapplication.data

import com.example.myapplication.domain.Product
import com.example.myapplication.domain.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(private val apiService: ProductApiService) : ProductRepository {
    override suspend fun fetchProducts(): Result<List<Product>> =
        runCatching { apiService.getProducts().products.map { it.toProduct() } }
}