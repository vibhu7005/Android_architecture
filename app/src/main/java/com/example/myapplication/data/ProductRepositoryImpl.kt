package com.example.myapplication.data

import com.example.myapplication.domain.Product
import com.example.myapplication.domain.ProductRepository
import com.example.myapplication.domain.utility.retryWithExponentialBackOff
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ProductRepositoryImpl(private val apiService: ProductApiService) : ProductRepository {
    override suspend fun fetchProducts(): Result<List<Product>> =

        runCatching {
            retryWithExponentialBackOff {
                apiService.getProducts().products.map { it.toProduct() }
            }
        }
}