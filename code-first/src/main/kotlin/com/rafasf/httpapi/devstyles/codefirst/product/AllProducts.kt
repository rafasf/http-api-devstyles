package com.rafasf.httpapi.devstyles.codefirst.product

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Container with products.")
data class AllProducts(
    @Schema(
        description = "All products known.",
        required = true
    )
    val data: List<ProductRepresentation>
)
