package com.springkotlin.controller

import com.springkotlin.controller.CatalogueServiceApplication
import com.springkotlin.controller.services.Greet
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [CatalogueServiceApplication::class]) // Go and run at random port[mostly not 8080]
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class GreetingControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Value("\${message}")
    lateinit var message: String

    @Test
    fun executeGreeting() {
        val name = "Anil"
        val result = webTestClient.get()
            .uri("/v1/greetings/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(Greet::class.java)
            .returnResult()
        Assertions.assertEquals(Greet("$message Anil"), result.responseBody)
    }
}