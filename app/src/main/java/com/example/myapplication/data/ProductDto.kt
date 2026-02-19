package com.example.myapplication.data

import com.example.myapplication.domain.Product

data class ProductDto(
    val id: Int,
    val title: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val thumbnail: String,
    val brand: String? =null,
    val category: String
) {
    fun toProduct(): Product = Product(
        id = id,
        title = title,
        price = price,
        discountPercentage = discountPercentage,
        rating = rating,
        thumbnail = thumbnail,
        brand = brand.orEmpty(),
        category = category
    )


}