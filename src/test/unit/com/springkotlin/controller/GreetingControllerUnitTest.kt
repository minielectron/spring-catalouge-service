package com.springkotlin.controller

import com.springkotlin.controller.controller.GreetingController
import com.springkotlin.controller.services.Greet
import com.springkotlin.controller.services.GreetingService
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient


@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
class GreetingControllerUnitTest {

    @MockBean
    lateinit var greetingServiceMock: GreetingService

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun executeGreeting() {
        val name = "Anil"

        every { greetingServiceMock.sayHello(name) } returns Greet(name)
        val result = webTestClient.get()
            .uri("/v1/greetings/{name}", name)
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(Greet::class.java)
            .returnResult()
        Assertions.assertEquals(Greet("Anil"), result.responseBody)
    }
}