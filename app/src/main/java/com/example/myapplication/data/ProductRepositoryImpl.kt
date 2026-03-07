package com.example.myapplication.data

import com.example.myapplication.safeApiCall
import domain.ProductRepository
import domain.models.Product
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val service: ProductApiService) :
    ProductRepository {
    override suspend fun getProducts(): Result<List<Product>> =
        safeApiCall {
            service.getProducts().products.map { it.toDomain() }
        }
}