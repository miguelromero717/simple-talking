package com.miguelromero717.simpletalking.shared.async

import org.springframework.amqp.core.AmqpTemplate

internal interface RabbitMQ {
    var queueTemplate: AmqpTemplate
    var queueName: String
    var exchangeName: String
    var routingKeyName: String
}
