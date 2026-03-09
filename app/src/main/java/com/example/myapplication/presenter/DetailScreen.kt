package com.example.myapplication.presenter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.presenter.viewmodel.ProductHomeScreenViewModel

@Composable fun DetailScreen(productId : String, viewModel: ProductHomeScreenViewModel = hiltViewModel()) {
    Column(modifier = Modifier.systemBarsPadding()) {
        Text("product id is $productId")
    }
}