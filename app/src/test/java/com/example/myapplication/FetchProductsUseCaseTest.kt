package com.example.myapplication

import com.example.myapplication.domain.FetchProductsUseCase
import com.example.myapplication.domain.Product
import com.example.myapplication.domain.ProductRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchProductsUseCaseTest {

    private lateinit var productRepository: FakeProductRepository
    private lateinit var fetchProductsUseCase: FetchProductsUseCase

    @Before
    fun setUp() {
        productRepository = FakeProductRepository()
        fetchProductsUseCase = FetchProductsUseCase(productRepository)
    }

    @Test
    fun `fetchProducts_returns`() = runTest {
        // Given
        val expectedProducts = listOf(
            Product(1, "Product 1", 23.5, 10.0, 4.5, "rtr", "rt", "trt"),
        )
        productRepository.result = Result.success(expectedProducts)

        // When
        val result = fetchProductsUseCase()

        // Then
        assert(result.isSuccess)
        assert(result.getOrNull() == expectedProducts)
    }
}


class FakeProductRepository : ProductRepository {

    var result = Result.success(
        listOf(
            Product(1, "Product 1", 23.5, 10.0, 4.5,"rtr", "rt", "trt"),
        )
    )
    override suspend fun fetchProducts(): Result<List<Product>> {
        return result
    }
}