package com.example.myapplication.data

import com.example.myapplication.domain.Product
import com.example.myapplication.domain.ProductRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ProductRepositoryImpl @Inject constructor(private val apiService: ProductApiService) :
    ProductRepository {
    override suspend fun fetchProducts(): Result<List<Product>> {
        try {
            val res = apiService.getProducts()
            return Result.success(res.products.map { it.toProduct() })
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}