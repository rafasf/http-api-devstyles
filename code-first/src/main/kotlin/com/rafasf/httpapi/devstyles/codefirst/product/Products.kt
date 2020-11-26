package com.rafasf.httpapi.devstyles.codefirst.product

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/products", produces = [MediaType.APPLICATION_JSON_VALUE])
class Products {
    @GetMapping
    fun all(): ResponseEntity<AllProducts> {
        TODO()
    }
}
