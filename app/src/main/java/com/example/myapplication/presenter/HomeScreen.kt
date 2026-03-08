package com.example.myapplication.presenter

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen() {
    val navController = LocalNavController.current

    LazyColumn {
        items(10) {
            Button(
                onClick = {
                    val route = Details(it.toString())
                    navController.navigate(route)
                }
            ) {
                Text("Click me")
            }
        }
    }
}