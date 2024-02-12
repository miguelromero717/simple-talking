package com.miguelromero717.simpletalking.shared.async

import com.miguelromero717.simpletalking.shared.utils.ObjectMapper
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired

abstract class RabbitMQProducer<T>(
    override var queueName: String,
    override var exchangeName: String,
    override var routingKeyName: String
) : RabbitMQ {
    
    @Autowired
    override lateinit var rabbitTemplate: RabbitTemplate
    
    fun publish(payload: T) {
        try {
            rabbitTemplate.convertAndSend(
                exchangeName,
                routingKeyName,
                ObjectMapper.commonMapper.writeValueAsString(payload)
            )
        } catch (e: Exception) {
            throw e
        }
    }
}
