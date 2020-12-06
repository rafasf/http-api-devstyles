package com.rafasf.httpapi.devstyles.contract

import au.com.dius.pact.consumer.MockServer
import au.com.dius.pact.consumer.dsl.PactDslJsonBody
import au.com.dius.pact.consumer.dsl.PactDslWithProvider
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt
import au.com.dius.pact.consumer.junit5.PactTestFor
import au.com.dius.pact.core.model.RequestResponsePact
import au.com.dius.pact.core.model.annotations.Pact
import org.apache.http.client.fluent.Request
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(PactConsumerTestExt::class)
class ProductsContract {
    @Pact(provider = "ProductsProvider", consumer = "Self")
    fun `returns all products`(builder: PactDslWithProvider): RequestResponsePact {
        return builder
            .given("products exist")
            .uponReceiving("Get all products")
            .path("/v1/products")
            .method("GET")
            .willRespondWith()
            .status(200)
            .body(
                PactDslJsonBody()
                    .minArrayLike("data", 1)
                    .stringType("id")
                    .stringType("description")
                    .closeObject()
                    .closeArray()
            ).toPact()
    }

    @Pact(provider = "ProductsProvider", consumer = "Self")
    fun `returns an empty list when there no products`(builder: PactDslWithProvider): RequestResponsePact {
        return builder
            .given("no products exist")
            .uponReceiving("Get all products")
            .path("/v1/products")
            .method("GET")
            .willRespondWith()
            .status(200)
            .body(
                PactDslJsonBody()
                    .minArrayLike("data", 0)
                    .closeObject()
            ).toPact()
    }

    @Pact(provider = "ProductsProvider", consumer = "Self")
    fun `returns error when something goes wrong`(builder: PactDslWithProvider): RequestResponsePact {
        return builder
            .given("something goes wrong")
            .uponReceiving("Get all products")
            .path("/v1/products")
            .method("GET")
            .willRespondWith()
            .status(500)
            .headers(
                mutableMapOf(
                    Pair("Content-Type", "application/problem+json")
                )
            )
            .body(
                PactDslJsonBody()
                    .stringValue("title", "Ooops")
                    .stringValue("type", "about:blank")
                    .stringValue("detail", "Failed to get all the necessary information")
                    .numberValue("status", 503)
            ).toPact()
    }

    @Test
    @PactTestFor(pactMethod = "returns all products")
    fun products(mockServer: MockServer) {
        val httpResponse = Request.Get(mockServer.getUrl() + "/v1/products").execute().returnResponse()
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200)
    }

    @Test
    @PactTestFor(pactMethod = "returns an empty list when there no products")
    fun noProducts(mockServer: MockServer) {
        val httpResponse = Request.Get("${mockServer.getUrl()}/v1/products").execute().returnResponse()
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200)
    }

    @Test
    @PactTestFor(pactMethod = "returns error when something goes wrong")
    fun someeError(mockServer: MockServer) {
        val httpResponse = Request.Get("${mockServer.getUrl()}/v1/products").execute().returnResponse()
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(500)
    }
}
