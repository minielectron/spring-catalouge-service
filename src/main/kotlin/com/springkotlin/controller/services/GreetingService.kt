package com.springkotlin.controller.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GreetingService {

    // Used to read the value from properties file.
    // We can also specify the default value of it to avoid IllegalArgumentException as follow
    // @Value("${key_name:default_value}")
    @Value("\${message}")
    lateinit var message: String

    fun sayHello(name: String): Greet {
        return Greet("$message $name")
    }
}

data class Greet(val name: String, val status: String = "happy")