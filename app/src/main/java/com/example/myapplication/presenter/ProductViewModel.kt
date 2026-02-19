package com.example.myapplication.presenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.FetchProductsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val fetchProductsUseCase: FetchProductsUseCase) : ViewModel() {

    private val _productUiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
    val productUiState = _productUiState.asStateFlow()

    private val _productEventFlow = MutableSharedFlow<ProductEvent>()
    val productEventFlow = _productEventFlow.asSharedFlow()

    init {
        fetchProducts()
    }

    fun serveIntents(intent: ProductIntent) {
        when (intent) {
            ProductIntent.RefreshProducts -> fetchProducts()
        }
    }

    private fun fetchProducts() {
        Log.d("vaibhav", "method called")
        _productUiState.value = ProductUiState.Loading
        viewModelScope.launch {
            fetchProductsUseCase()
                .onSuccess {
                    Log.d("vaibhav", "Success $it")
                    _productUiState.value = ProductUiState.Success(it)
                }.onFailure {
                    Log.d("vaibhav", "onFailure ${it.message}")
                    _productEventFlow.emit(ProductEvent.Error(it.message.orEmpty()))
                }
        }
    }

}