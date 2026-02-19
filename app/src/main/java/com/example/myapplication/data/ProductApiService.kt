package com.example.myapplication.data

import com.example.myapplication.domain.Product
import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts() : List<Product>
}