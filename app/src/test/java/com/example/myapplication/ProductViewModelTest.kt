package com.example.myapplication

import app.cash.turbine.test
import com.example.myapplication.domain.FetchProductsUseCase
import com.example.myapplication.presenter.ProductViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class ProductViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

   @Before
   fun setUp() {
       Dispatchers.setMain(testDispatcher)
   }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    fun createViewModel(): ProductViewModel {
        val repository = FakeProductRepository()
        val useCase = FetchProductsUseCase(repository)
        return ProductViewModel(useCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `initial_state_is_loading_before_fetch_completes`() = runTest(testDispatcher) {
        val viewmodel = createViewModel()
        val uiState = viewmodel.productUiState
        uiState.test {
            val initialState = awaitItem()
            assert(initialState is com.example.myapplication.presenter.ProductUiState.Loading)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `initial_data_load_success`() = runTest(testDispatcher) {
        // Given
       val viewModel = createViewModel()
        val uiState = viewModel.productUiState
        uiState.test {
            advanceUntilIdle()
            val state = awaitItem()
            val successState = awaitItem()
            assert(successState is com.example.myapplication.presenter.ProductUiState.Success)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `initial_data_load_failure`() = runTest(testDispatcher) {
        // Given
        val repository = FakeProductRepository()
        repository.result = Result.failure(Exception("Network error"))
        val useCase = FetchProductsUseCase(repository)
        val viewModel = ProductViewModel(useCase)
        
        // When - Advance coroutines to let fetchProducts complete
        advanceUntilIdle()
        
        // Then
        viewModel.productUiState.test {
            // First state: Loading
            val loadingState = awaitItem()
            assert(loadingState is com.example.myapplication.presenter.ProductUiState.Loading)
            
            // Second state: Idle (after failure)
            val idleState = awaitItem()
            assert(idleState is com.example.myapplication.presenter.ProductUiState.Idle)
        }
    }
}