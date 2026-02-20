package com.example.myapplication.data

import com.example.myapplication.domain.Pokemon
import com.example.myapplication.domain.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepositoryImpl(private val apiService: ProductApiService) : PokemonRepository {
    override suspend fun fetchProducts(): Result<List<Pokemon>> = withContext(Dispatchers.IO) {
        runCatching {
            apiService.getProducts().results.map { it.mapToPokemon() }
        }
    }
}