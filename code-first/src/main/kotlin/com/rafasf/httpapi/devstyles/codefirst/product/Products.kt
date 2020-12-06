package com.rafasf.httpapi.devstyles.codefirst.product

import arrow.core.ListK
import arrow.syntax.function.pipe
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
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
            .fold(
                    { error -> toInternalError(error) },
                    { products -> toAllProducts(products).pipe { ok(it) } }
            )

    private fun toAllProducts(products: ListK<Product>): AllProducts {
        return products
                .map { toProductRepresentation(it) }
                .foldLeft(
                        AllProducts(emptyList()),
                        { allProducts, productRepresentation ->
                            allProducts.copy(allProducts.data.plus(productRepresentation))
                        })
    }

    private fun toInternalError(error: ProductSourceError): ResponseEntity<ProblemRepresentation> {
        val problemRepresentation = when (error) {
            is NoProducts -> ProblemRepresentation(
                    title = error.title,
                    detail = error.description,
                    status = 500,
                    type = "about:blank")
        }
        return ResponseEntity
                .status(problemRepresentation.status)
                .body(problemRepresentation)
    }
}
