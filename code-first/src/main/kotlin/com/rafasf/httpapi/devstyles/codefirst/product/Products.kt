package com.rafasf.httpapi.devstyles.codefirst.product

import arrow.syntax.function.pipe
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/products", produces = [MediaType.APPLICATION_JSON_VALUE])
class Products(
    private val productsSource: ProductsSource
) {
    @GetMapping
    fun all() = productsSource
            .all()
            .map { toProductRepresentation(it) }
            .foldLeft(AllProducts(emptyList())) {
                allProducts, productRepresentation -> allProducts.copy(allProducts.data.plus(productRepresentation))
            }
            .pipe { ok(it) }
}
