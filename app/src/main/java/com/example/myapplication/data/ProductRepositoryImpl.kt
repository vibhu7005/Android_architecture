package com.example.myapplication.data

import com.example.myapplication.domain.Product
import com.example.myapplication.domain.ProductRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(
    val apiService: ProductApiService) :
    ProductRepository, BaseRepository() {
    override suspend fun fetchProducts(): Result<List<Product>> {
        return executeApiCall {
            apiService.getProducts().products
        }.map { dtos ->
            dtos.map { it.toProduct() }
        }
    }
}