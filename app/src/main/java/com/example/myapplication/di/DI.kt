package com.example.myapplication.di

import com.example.myapplication.data.ProductApiService
import com.example.myapplication.data.ProductRepositoryImpl
import com.example.myapplication.data.RetrofitInstance
import com.example.myapplication.domain.FetchProductsUseCase
import com.example.myapplication.domain.ProductRepository

object DI {
    val productApiService : ProductApiService by lazy { RetrofitInstance.productApiService }
    val productRepository: ProductRepository by lazy { ProductRepositoryImpl(productApiService) }
    val fetchProductsUseCase: FetchProductsUseCase by lazy { FetchProductsUseCase(productRepository) }
}