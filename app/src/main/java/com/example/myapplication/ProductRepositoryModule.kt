package com.example.myapplication

import com.example.myapplication.data.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import domain.ProductRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface ProductRepositoryModule {
    @Binds
    @Singleton
    fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository
}