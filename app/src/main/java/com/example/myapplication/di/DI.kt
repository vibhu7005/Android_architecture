package com.example.myapplication.di

import com.example.myapplication.data.ProductApiService
import com.example.myapplication.data.ProductRepositoryImpl
import com.example.myapplication.data.RetrofitInstance
import com.example.myapplication.domain.FetchPokemonUseCase
import com.example.myapplication.domain.PokemonRepository

object DI {

    val productApiService : ProductApiService by lazy { RetrofitInstance.productApiService }
    val productRepository: PokemonRepository by lazy { ProductRepositoryImpl(productApiService) }
    val fetchProductsUseCase: FetchPokemonUseCase by lazy { FetchPokemonUseCase(productRepository) }
}