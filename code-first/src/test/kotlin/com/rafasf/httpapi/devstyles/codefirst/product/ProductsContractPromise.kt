package com.rafasf.httpapi.devstyles.codefirst.product

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
@PactTestFor("ProductsProvider")
class ProductsContractPromise {
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

    @Test
    fun test(mockServer: MockServer) {
        val httpResponse = Request.Get(mockServer.getUrl() + "/v1/products").execute().returnResponse()
        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200)
    }
}
