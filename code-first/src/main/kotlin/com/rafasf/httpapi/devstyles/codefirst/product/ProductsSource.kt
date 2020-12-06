package com.rafasf.httpapi.devstyles.codefirst.product

import arrow.core.ListK
import org.springframework.stereotype.Component

@Component
class ProductsSource {
    fun all(): ListK<Product> {
        TODO()
    }
}
