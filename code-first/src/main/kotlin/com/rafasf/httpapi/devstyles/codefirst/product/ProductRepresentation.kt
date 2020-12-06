package com.rafasf.httpapi.devstyles.codefirst.product

import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    description = "A representation of the Product."
)
data class ProductRepresentation(
    @Schema(
        description = "Product identifier.",
        example = "dp54pp",
        required = true
    )
    val id: String,

    @Schema(
        description = "Product description.",
        example = "Not Fast, Not Furious",
        required = true
    )
    val description: String
)

fun toProductRepresentation(product: Product) = ProductRepresentation(
        id = product.id,
        description = product.description
)
