package com.example.myapplication.domain

data class Product(val id : String,
    val title : String,
    val price : Int,
    val discountPrice : Int,
    val rating : Int,
    val thumbnailUrl : String,
    val brand : String,
    val category : String
)

