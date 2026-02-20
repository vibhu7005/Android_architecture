package com.example.myapplication.presenter

sealed class Routes(val route: String) {
    data object ProductList : Routes("product_list")
    data object ProductDetail : Routes("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
}