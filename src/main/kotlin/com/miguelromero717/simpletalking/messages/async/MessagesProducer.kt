package com.miguelromero717.simpletalking.messages.async

import com.miguelromero717.simpletalking.shared.async.RabbitMQProducer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MessagesProducer(
    @Value("\${rabbitmq.queue.name}")
    private val queue: String,
    @Value("\${rabbitmq.queue.exchange}")
    private val exchange: String,
    @Value("\${rabbitmq.queue.routingKey}")
    private val routingKey: String,
) : RabbitMQProducer<MessageSchema>(
        queueName = queue,
        exchangeName = exchange,
        routingKeyName = routingKey,
    )
