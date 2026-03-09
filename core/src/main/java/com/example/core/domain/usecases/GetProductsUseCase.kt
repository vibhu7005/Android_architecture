package com.example.core.domain.usecases

import com.example.core.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val repository: ProductRepository) {

    suspend operator fun invoke() =
        repository.getProducts()
}