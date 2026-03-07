package domain.usecases

import domain.ProductRepository
import domain.models.Product
import javax.inject.Inject

class FetchProductsListUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(): Result<List<Product>> = repository.getProducts()
}