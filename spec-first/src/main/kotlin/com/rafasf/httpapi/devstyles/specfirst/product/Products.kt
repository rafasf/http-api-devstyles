package com.rafasf.httpapi.devstyles.specfirst.product

import com.rafasf.httpapi.devstyles.specfirst.http.ProductsApi
import com.rafasf.httpapi.devstyles.specfirst.http.representations.AllProducts
import org.springframework.http.ResponseEntity

class Products : ProductsApi {
    override fun all(): ResponseEntity<AllProducts> {
        TODO()
    }
}
