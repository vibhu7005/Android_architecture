package com.example.myapplication.data

import com.example.myapplication.domain.Product
import com.example.myapplication.domain.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryImpl() : ProductRepository {
    override suspend fun fetchProducts(): Result<List<Product>> =
        withContext(Dispatchers.IO) {
            runCatching { RetrofitInstance.productApiService.getProducts().products }
        }
}