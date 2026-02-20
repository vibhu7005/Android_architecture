package com.example.myapplication.presenter

sealed class PokemonIntent {
    data object Refresh : PokemonIntent()
}

