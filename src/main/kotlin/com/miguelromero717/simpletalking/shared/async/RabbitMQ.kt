package com.miguelromero717.simpletalking.shared.async

import org.springframework.amqp.rabbit.core.RabbitTemplate

internal interface RabbitMQ {
    
    var rabbitTemplate: RabbitTemplate
    var queueName: String
    var exchangeName: String
    var routingKeyName: String
}