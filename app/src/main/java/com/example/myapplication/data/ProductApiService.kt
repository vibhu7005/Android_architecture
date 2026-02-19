package com.example.myapplication.data

import com.example.myapplication.domain.Product
import retrofit2.http.GET

data class ProductResponse(
    val products: List<ProductDto>
)

interface ProductApiService {
    @GET("products")
    suspend fun getProducts() : ProductResponse
}