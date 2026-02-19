package com.example.myapplication.presenter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.di.DI
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // DI setup
        val factory = GenericViewModelFactory(ProductViewModel::class.java) {
            ProductViewModel(DI.fetchProductsUseCase)
        }

        // Create ViewModel with factory
        viewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]

        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val productState by viewModel.productUiState.collectAsState()

                    LaunchedEffect(Unit) {
                        viewModel.productEventFlow.collect {
                            when (it) {
                                is ProductEvent.Error -> {
                                    Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                    when (productState) {
                        ProductUiState.Loading -> {
                            Text(
                                text = "Loading products...",
                                modifier = Modifier.padding(innerPadding)
                            )
                        }

                        is ProductUiState.Success -> {
                            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                                items(
                                    (productState as ProductUiState.Success).products,
                                    key = { it.id }) {
                                    Text(text = it.title, modifier = Modifier.padding(8.dp))
                                }
                            }
                        }

                        ProductUiState.IDLE -> {}
                    }


                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}