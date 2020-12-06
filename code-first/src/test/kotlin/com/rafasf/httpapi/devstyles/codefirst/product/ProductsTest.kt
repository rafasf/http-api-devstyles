package com.rafasf.httpapi.devstyles.codefirst.product

import arrow.core.Either
import arrow.core.listKOf
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(Products::class)
class ProductsTest {
    @Autowired
    lateinit var mockMvc: MockMvc
    @MockBean
    lateinit var productsSource: ProductsSource

    @Test
    fun `returns all products`() {
        val product = Product("oueorurworrowr", "the product description")

        given(productsSource.all()).willReturn(Either.right(listKOf(product)))

        mockMvc.get("/v1/products")
            .andExpect { status { isOk() } }
            .andExpect { jsonPath("$.data[0].id", equalTo(product.id)) }
            .andExpect { jsonPath("$.data[0].description", equalTo(product.description)) }
    }

    @Test
    fun `retuns empty when no products exist`() {
        given(productsSource.all()).willReturn(Either.right(listKOf()))

        mockMvc.get("/v1/products")
            .andExpect { status { isOk() } }
            .andExpect { jsonPath<Collection<Any>>("$.data", hasSize(0)) }
    }

    @Test
    fun `returns an error when something goes wrong`() {
        given(productsSource.all()).willReturn(Either.left(NoProducts(description = "ops")))

        mockMvc.get("/v1/products")
            .andExpect { status { isInternalServerError() } }
            .andExpect { jsonPath("$.title", equalTo("No products")) }
            .andExpect { jsonPath("$.type", equalTo("about:blank")) }
            .andExpect { jsonPath("$.status", equalTo(500)) }
            .andExpect { jsonPath("$.detail", equalTo("ops")) }
    }
}
