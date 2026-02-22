package com.example.myapplication.data

import retrofit2.http.GET

data class ProductResponse(
//    val products: List<ProductDto>
     val products: List<String>

)

interface ProductApiService {
    @GET("products")
    suspend fun getProducts() : ProductResponse
}