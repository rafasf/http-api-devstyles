package com.rafasf.httpapi.devstyles.codefirst.product

typealias ProductId = String

data class Product(
        val id: ProductId,
        val description: String
)
