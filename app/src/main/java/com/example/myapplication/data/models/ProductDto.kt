package com.example.myapplication.data.models

import domain.models.Product

data class ProductDto(val id : String, val title : String, val thumbnail : String) {
    fun toDomain() = Product(id = id, title = title, thumbnail = thumbnail)
}
