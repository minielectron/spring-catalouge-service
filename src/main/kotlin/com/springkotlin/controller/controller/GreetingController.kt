package com.springkotlin.controller.controller

import com.springkotlin.controller.services.Greet
import com.springkotlin.controller.services.GreetingService
import io.github.oshai.kotlinlogging.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greetings")
class GreetingController(val greetingService: GreetingService) {

    companion object : KLogging()

    @GetMapping("/{name}")
    fun executeGreetings(@PathVariable("name") name: String): ResponseEntity<Greet> {
        logger.info { "Calling hello world function" }
        return ResponseEntity(greetingService.sayHello(name), HttpStatus.OK)
    }

}