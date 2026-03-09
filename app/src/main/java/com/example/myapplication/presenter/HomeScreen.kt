package com.example.myapplication.presenter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.presenter.uiState.ProductUiState
import com.example.myapplication.presenter.viewmodel.ProductHomeScreenViewModel


@Composable
fun HomeScreen  (viewModel: ProductHomeScreenViewModel = hiltViewModel()) {
    val res = viewModel.uiState.collectAsStateWithLifecycle()

    val result = res.value
    val navController = LocalNavController.current

    Column(modifier = Modifier.systemBarsPadding()) {
        when (result) {
            is ProductUiState.Error -> Text(text = result.message)

            is ProductUiState.Loading -> {}

            is ProductUiState.Success -> {
                LazyColumn(
                ) {
                    items(items = result.products, key = { it.id }) {
                        Button ({
                            navController.navigate(Detail(it.id))
                        }) {
                            Text(text = "Click me")
                        }
                        Text(
                            text = it.title,
                        )
                    }
                }
            }

            is ProductUiState.Empty -> {}

        }
    }
}