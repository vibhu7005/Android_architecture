package com.example.myapplication.data

import retrofit2.http.GET

data class PokemonResponse(
    val results: List<PokemonDto>
)

interface ProductApiService {
    @GET("pokemon")
    suspend fun getProducts() : PokemonResponse
}