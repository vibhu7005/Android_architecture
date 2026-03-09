package com.example.myapplication.data.repo

import com.example.core.domain.model.Product
import com.example.core.domain.repository.ProductRepository
import com.example.myapplication.data.model.toProduct
import com.example.myapplication.data.network.ProductApiService
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val api : ProductApiService) : ProductRepository, SafeApiCall {
    override suspend fun getProducts(): Result<List<Product>> = safeApiCall {
        api.getProducts().products.map { it.toProduct()}
    }

    override suspend fun getProductsById(id: String): Result<Product> = safeApiCall {
        api.getProductById(id).toProduct()
    }

}