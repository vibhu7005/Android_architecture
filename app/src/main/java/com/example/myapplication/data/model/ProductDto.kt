package com.example.myapplication.data.model

import com.example.core.domain.model.Product

data class ProductDto(
    val id: String,
    val title: String,
//    val price: Int,
//    val discountPercentage: Int,
//    val rating: Int,
//    val stock: Int,
//    val category: String,
    val imageUrl: String?=null
)

fun ProductDto.toProduct() : Product {
    return Product(id, title, imageUrl)
}