package com.example.myapplication.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecases.GetProductsUseCase
import com.example.myapplication.presenter.uiState.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductHomeScreenViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {


    private val _uiState : MutableStateFlow<ProductUiState> = MutableStateFlow(ProductUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            getProductsUseCase().onSuccess {
                if (it.isEmpty()) _uiState.value = ProductUiState.Empty
                else _uiState.value = ProductUiState.Success(it)
            }.onFailure {
                _uiState.value = ProductUiState.Error(it.message ?: "Default Error")
            }
        }
    }


}