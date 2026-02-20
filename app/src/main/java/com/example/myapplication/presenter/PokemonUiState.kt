package com.example.myapplication.presenter

import com.example.myapplication.domain.Pokemon

sealed class PokemonUiState {
    data class Success(val pokemonList : List<Pokemon>) : PokemonUiState()
    object Loading : PokemonUiState()

    object IDLE : PokemonUiState()
}