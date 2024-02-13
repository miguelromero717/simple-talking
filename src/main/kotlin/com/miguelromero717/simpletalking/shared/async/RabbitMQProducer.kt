package com.miguelromero717.simpletalking.shared.async

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier

abstract class RabbitMQProducer<T>(
    override var queueName: String,
    override var exchangeName: String,
    override var routingKeyName: String,
) : RabbitMQ {
    @Autowired
    @Qualifier("amqpTemplate")
    override lateinit var queueTemplate: AmqpTemplate

    fun publish(payload: T) {
        try {
            queueTemplate.convertAndSend(
                exchangeName,
                routingKeyName,
                payload,
            )
        } catch (e: Exception) {
            throw e
        }
    }
}
