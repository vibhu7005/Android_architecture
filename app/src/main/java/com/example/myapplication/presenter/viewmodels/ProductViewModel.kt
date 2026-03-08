package com.example.myapplication.presenter.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.presenter.ProductUiState
import com.example.myapplication.presenter.toUiModel
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.usecases.FetchProductsListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val useCase: FetchProductsListUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = ProductUiState.Loading
            useCase().onSuccess {
                Log.d("vaibhav", "fetchProducts: ${it.size}")
                _uiState.value = ProductUiState.Success(it.map { product -> product.toUiModel() })
            }
                .onFailure {
                    Log.d("vaibhav", "fetchProducts: ${it.message}")
                    _uiState.value = ProductUiState.Error(it.message.orEmpty())
                }
        }

    }


}