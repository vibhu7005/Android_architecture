package com.example.myapplication.domain

class FetchPokemonUseCase(private val repository: PokemonRepository) {
    suspend operator fun invoke() = repository.fetchProducts()
}