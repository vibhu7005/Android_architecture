package com.example.core.domain.model

data class Product(
    val id: String,
    val title: String,
//    val price: Int,
//    val discountPercentage: Int,
//    val rating: Int,
//    val stock: Int,
//    val category: String,
    val imageUrl: String?=null
)



//sealed interface Producta {
//    data class AdProduct(val id : String, )
//
//    data class GeneralProduct()
//}