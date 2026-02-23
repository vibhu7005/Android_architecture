package com.example.myapplication

import com.example.myapplication.data.ProductApiService
import com.example.myapplication.data.ProductDto
import com.example.myapplication.data.ProductRepositoryImpl
import com.example.myapplication.data.ProductResponse
import com.example.myapplication.domain.Product
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ProductRepoImplTest {
    private lateinit var productRepoImpl: ProductRepositoryImpl
    private lateinit var apiService: ProductFakeApiService

    @Before
    fun setUp() {
        apiService = ProductFakeApiService()
        productRepoImpl = ProductRepositoryImpl(apiService)
    }



    @Test
    fun `fetchProducts returns expected products`() = runTest {
        // Given
        val expectedProducts = listOf(
            Product(1, "Product 1", 23.5, 10.0, 4.5, "rtr", "rt", "trt"),
        )
        apiService.result = ProductResponse(
            products = listOf(
                ProductDto(1, "Product 1", 23.5, 10.0, 4.5, "rtr", "rt", "trt")
            )
        )

        // When
        val result = productRepoImpl.fetchProducts()

        // Then
        assert(result.isSuccess)
        assert(result.getOrNull() == expectedProducts)
    }
}

class ProductFakeApiService : ProductApiService {

    var result = ProductResponse(
        products = emptyList())

    override suspend fun getProducts(): ProductResponse = result
}