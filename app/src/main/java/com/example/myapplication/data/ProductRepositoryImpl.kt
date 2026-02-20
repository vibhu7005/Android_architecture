package com.example.myapplication.data

import com.example.myapplication.domain.Product
import com.example.myapplication.domain.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(private val apiService: ProductApiService) : ProductRepository {

    var cachedProducts: List<Product> = emptyList()
    override suspend fun fetchProducts(): Result<List<Product>> = withContext(Dispatchers.IO) {
        runCatching {
            apiService.getProducts().products.map {
                it.toProduct()
            }.also {
                cachedProducts = it
            }
        }
    }

    override suspend fun filterProducts(query: String): Result<List<Product>> = runCatching {
        cachedProducts.filter { it.title.contains(query) }
    }
}