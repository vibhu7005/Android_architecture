package com.example.myapplication.data.network

import com.example.myapplication.data.model.ProductDto
import com.example.myapplication.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): ProductResponse
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id : String) : ProductDto
}
