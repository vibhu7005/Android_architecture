package com.example.myapplication.data.network

import com.example.core.domain.repository.ProductRepository
import com.example.myapplication.data.repo.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface ProductRepositoryModule {
    @Binds
    @Singleton
    fun provideRepository(impl : ProductRepositoryImpl) : ProductRepository
}