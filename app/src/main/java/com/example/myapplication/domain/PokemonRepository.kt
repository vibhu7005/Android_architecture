package com.example.myapplication.domain

interface PokemonRepository {
    suspend fun fetchProducts() : Result<List<Pokemon>>
}