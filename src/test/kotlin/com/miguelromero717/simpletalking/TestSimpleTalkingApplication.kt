package com.miguelromero717.simpletalking

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestSimpleTalkingApplication {
    
    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
    }
    
    @Bean
    @ServiceConnection
    fun rabbitContainer(): RabbitMQContainer {
        return RabbitMQContainer(DockerImageName.parse("rabbitmq:latest"))
    }
    
}

fun main(args: Array<String>) {
    fromApplication<SimpleTalkingApplication>().with(TestSimpleTalkingApplication::class).run(*args)
}
