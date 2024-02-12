package com.miguelromero717.simpletalking.messages.async

import com.miguelromero717.simpletalking.messages.service.IMessagesService
import com.miguelromero717.simpletalking.shared.async.RabbitMQConsumer
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class MessagesConsumer(
    private val messagesService: IMessagesService
) : RabbitMQConsumer<MessageSchema>() {
    
    @RabbitListener(queues = ["\${rabbitmq.queue.name}"])
    override fun consume(payload: MessageSchema) {
        messagesService.processMessage(payload)
    }
}