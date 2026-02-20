package com.example.myapplication.presenter

sealed class PokemonEvent {
    data class Error(val message : String) : PokemonEvent()
}