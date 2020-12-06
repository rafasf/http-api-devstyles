package com.rafasf.httpapi.devstyles.codefirst.product

data class ProblemRepresentation(
        val title: String,
        val type: String,
        val detail: String,
        val status: Int
)
