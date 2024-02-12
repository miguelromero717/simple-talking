package com.miguelromero717.simpletalking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleTalkingApplication

fun main(args: Array<String>) {
    runApplication<SimpleTalkingApplication>(*args)
}
