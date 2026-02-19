package com.example.myapplication.domain

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val thumbnail: String,
    val brand: String,
    val category: String
)

