package com.example.myapplication.data

import com.example.myapplication.domain.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository
}