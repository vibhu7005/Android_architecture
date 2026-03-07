package domain

import domain.models.Product

interface ProductRepository {
    suspend fun getProducts() : Result<List<Product>>
}