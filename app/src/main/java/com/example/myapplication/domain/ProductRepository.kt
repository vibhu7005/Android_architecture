package com.example.myapplication.domain

import com.example.myapplication.data.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

interface ProductRepository {
    suspend fun fetchProducts() : Result<List<Product>>
}