package com.example.myapplication.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.FetchPokemonUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PokemonViewModel(private val fetchProductsUseCase: FetchPokemonUseCase) : ViewModel() {

    private val _productUiState = MutableStateFlow<PokemonUiState>(PokemonUiState.Loading)
    val productUiState = _productUiState.asStateFlow()

    private val _productEventFlow = MutableSharedFlow<PokemonEvent>()
    val productEventFlow = _productEventFlow.asSharedFlow()

    init {
        fetchPokemon()
    }

    fun serveIntents(intent: PokemonIntent) {
        when (intent) {
            PokemonIntent.Refresh -> fetchPokemon()
        }
    }

    private fun fetchPokemon() {
        Log.d("vaibhav", "method called")
        _productUiState.value = PokemonUiState.Loading
        viewModelScope.launch {
            fetchProductsUseCase()
                .onSuccess {
                    Log.d("vaibhav", "Success $it")
                    _productUiState.value = PokemonUiState.Success(it)
                }.onFailure {
                    Log.d("vaibhav", "onFailure ${it.message}")
                    _productUiState.value = PokemonUiState.IDLE
                    _productEventFlow.emit(PokemonEvent.Error(it.message.orEmpty()))
                }
        }
    }

}