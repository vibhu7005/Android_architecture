package com.example.myapplication.presenter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.myapplication.domain.AddExpenseUseCase
import com.example.myapplication.domain.ExpenseRepo
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import javax.inject.Inject

val LocalNavController = compositionLocalOf<NavController> {
    error("No NavController provided")
}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var addExpenseUseCase: AddExpenseUseCase
    
    @Inject
    lateinit var expenseRepo: ExpenseRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Add sample data if database is empty
        lifecycleScope.launch {
            // Simple one-time check
            expenseRepo.observeExpenses().take(1).collect { expenses ->
                if (expenses.isEmpty()) {
                    val sampleExpenses = listOf(
                        Triple("Rent", "Bank Transfer", 1200.0),
                        Triple("Insurance", "Credit Card", 149.0),
                        Triple("Groceries", "Wallet", 205.0),
                        Triple("Utilities", "Credit Card", 180.0),
                        Triple("Bill", "Cash", 79.0)
                    )
                    
                    sampleExpenses.forEach { (name, method, amount) ->
                        addExpenseUseCase(name, method, amount).onSuccess {
                            println("Added expense: $name")
                        }.onFailure { error ->
                            println(" Failed to add $name: ${error.message}")
                        }
                    }
                }
            }
        }

        // DI setup

        // Create ViewModel with factory
//        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val expenseViewModel: ExpenseViewModel = viewModel()
                    ExpenseTableScreen(
                        viewModel = expenseViewModel,
                        onEditClick = { expense ->
                            // Handle edit click — navigate to edit screen, show dialog, etc.
                        }
                    )
                }
            }
        }

    }
}

//@Composable
//fun ProductListScreen(viewModel: ProductViewModel) {
//    val productState by viewModel.productUiState.collectAsStateWithLifecycle()
//    val context = LocalContext.current
//
//    LaunchedEffect(Unit) {
//        viewModel.productEventFlow.collect {
//            when (it) {
//                is ProductEvent.Error -> {
//                    Toast.makeText(
//                        context,
//                        it.message,
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }
//            }
//        }
//    }
//
//
//    val state = productState
//    val scrollState = rememberLazyGridState()
//    when (state) {
//        ProductUiState.Loading -> {
//            Text(
//                text = "Loading products...",
//            )
//        }
//
//        is ProductUiState.Success -> {
//            LazyVerticalGrid(columns = GridCells.Fixed(2), state = scrollState) {
//                items(
//                    state.products,
//                    key = { it.id }) {
//                    ProductItem(product = it)
//                }
//            }
//        }
//
//        ProductUiState.Idle -> {}
//    }
//}
//
//@Composable
//fun ProductItem(product: Product) {
//    val nav = LocalNavController.current
//    Column(modifier = Modifier.clickable {
//        nav.navigate(Routes.ProductDetail.createRoute(product.id))
////        }
//    }) {
//        AsyncImage(
//            model = product.thumbnail, // The image URL
//            contentDescription = "Sample Image", // For accessibility
//            modifier = Modifier.size(200.dp)
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            modifier = Modifier.fillMaxWidth(),
//            text = product.title,
//            textAlign = TextAlign.Center
//        )
//    }
//}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
//        Greeting("Android")
    }
}