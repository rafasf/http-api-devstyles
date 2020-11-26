package com.rafasf.httpapi.devstyles.specfirst

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpecFirstApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<SpecFirstApplication>(*args)
}
