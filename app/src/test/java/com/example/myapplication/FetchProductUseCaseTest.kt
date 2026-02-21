package com.example.myapplication

import com.example.myapplication.domain.FetchProductsUseCase
import com.example.myapplication.domain.ProductRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class FetchProductUseCaseTest {

    private lateinit var fetchProductsUseCase: FetchProductsUseCase
    private lateinit var productRepository: FakeProductRepository

    @Before
    fun setUp() {
        productRepository = FakeProductRepository()
        fetchProductsUseCase = FetchProductsUseCase(productRepository)
    }

    @Test
    fun `fetchProducts returns list of products successfully`() = runTest {
        productRepository.returnProducts = listOf(
            com.example.myapplication.domain.Product(
                id = 1,
                title = "Test Product",
                price = 9.99,
                discountPercentage = 4.5,
                rating = 4.0,
                brand = "Test Brand",
                category = "Test Category",
                thumbnail = "dfd",
            )
        )
        val result = fetchProductsUseCase()
        assert(result.isSuccess)
    }



}

class FakeProductRepository : ProductRepository {
    var returnProducts = listOf(
        com.example.myapplication.domain.Product(
            id = 1,
            title = "Test Product",
            price = 9.99,
            discountPercentage = 4.5,
            rating = 4.0,
            brand = "Test Brand",
            category = "Test Category",
            thumbnail = "dfd",
        )
    )

    override suspend fun fetchProducts(): Result<List<com.example.myapplication.domain.Product>> {
        return Result.success(returnProducts)
    }
}