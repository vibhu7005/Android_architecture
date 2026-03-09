package com.example.core.domain.usecases

import com.example.core.domain.model.Product
import com.example.core.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase {

    class GetProductsByIdUseCase @Inject constructor(private val repository: ProductRepository) {

        suspend operator fun invoke(id : String) : Result<Product> {
            if (id.isEmpty()) return Result.failure(Exception("Blank id is not allowed"))
            return repository.getProductsById(id)
        }
    }
}