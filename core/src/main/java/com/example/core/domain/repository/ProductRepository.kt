package com.example.core.domain.repository

import com.example.core.domain.model.Product

interface ProductRepository {
   suspend fun getProducts() : Result<List<Product>>
   suspend fun getProductsById(id : String) : Result<Product>
}