package com.rafasf.httpapi.devstyles.codefirst.product

import arrow.core.Either
import arrow.core.ListK
import org.springframework.stereotype.Component

sealed class ProductSourceError
data class NoProducts(val title: String = "No products", val description: String) : ProductSourceError()

@Component
class ProductsSource {
    fun all(): Either<ProductSourceError, ListK<Product>> {
        TODO()
    }
}
