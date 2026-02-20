package com.example.myapplication.data

import com.example.myapplication.domain.Pokemon

data class PokemonDto(
    val name : String,
    val url : String
) {
    fun mapToPokemon() : Pokemon {
        return Pokemon(
            name = name,
            url = url
        )
    }
}