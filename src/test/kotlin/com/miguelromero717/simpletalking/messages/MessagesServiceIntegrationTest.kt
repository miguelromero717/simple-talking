package com.miguelromero717.simpletalking.messages

import com.miguelromero717.simpletalking.SimpleTalkingApplication
import com.miguelromero717.simpletalking.messages.async.MessageSchema
import com.miguelromero717.simpletalking.messages.service.MessagesService
import com.miguelromero717.simpletalking.users.User
import com.miguelromero717.simpletalking.users.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.UUID

@SpringBootTest
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [SimpleTalkingApplication::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MessagesServiceIntegrationTest {
    
    @Autowired
    private lateinit var messagesService: MessagesService
    
    @Autowired
    private lateinit var messageRepository: MessageRepository
    
    @Autowired
    private lateinit var userRepository: UserRepository
    
    @Test
    fun `test process message`() {
        val senderId = UUID.randomUUID().toString()
        val sender = userRepository.saveAndFlush(User(nickname = UUID.randomUUID().toString(), externalId = senderId))
        val receiverId = UUID.randomUUID().toString()
        val receiver = userRepository.saveAndFlush(User(nickname = UUID.randomUUID().toString(), externalId = receiverId))
        val payload = MessageSchema(senderId, receiverId, "Hello, World!")
        
        messagesService.processMessage(payload)
        
        val messagesReceived = messageRepository.findBySender(sender = sender)
        assertEquals(1, messagesReceived.size)
        assertEquals(payload.payload, messagesReceived[0].payload)
        assertEquals(receiver.externalId, messagesReceived[0].receiver.externalId)
    }
    
    @Test
    fun `test get messages received`() {
        val senderId = UUID.randomUUID().toString()
        val sender = userRepository.saveAndFlush(User(nickname = UUID.randomUUID().toString(), externalId = senderId))
        val receiverId = UUID.randomUUID().toString()
        val receiver = userRepository.saveAndFlush(User(nickname = UUID.randomUUID().toString(), externalId = receiverId))
        val message = Message(
            sender = sender,
            receiver = receiver,
            payload = "Hello"
        )
        messageRepository.save(message)
        
        val result = messagesService.getMessagesReceived(receiverId)
        assertEquals(1, result.size)
        assertEquals("Hello", result[0].payload)
    }
    
    @Test
    fun `test get messages sent`() {
        val senderId = UUID.randomUUID().toString()
        val sender = userRepository.saveAndFlush(User(nickname = UUID.randomUUID().toString(), externalId = senderId))
        val receiverId = UUID.randomUUID().toString()
        val receiver = userRepository.saveAndFlush(User(nickname = UUID.randomUUID().toString(), externalId = receiverId))
        val message = Message(
            sender = sender,
            receiver = receiver,
            payload = "Hello"
        )
        messageRepository.save(message)
        
        val result = messagesService.getMessagesSent(senderId)
        assertEquals(1, result.size)
        assertEquals("Hello", result[0].payload)
    }
    
    @Test
    fun `test get messages received from specific sender`() {
        val senderId = UUID.randomUUID().toString()
        val sender = userRepository.saveAndFlush(User(nickname = UUID.randomUUID().toString(), externalId = senderId))
        val receiverId = UUID.randomUUID().toString()
        val receiver = userRepository.saveAndFlush(User(nickname = UUID.randomUUID().toString(), externalId = receiverId))
        val message =
            Message(
                sender = sender,
                receiver = receiver,
                payload = "Hello"
            )
        messageRepository.save(message)
        
        val result = messagesService.getMessagesReceivedFromSpecificSender(receiverId, senderId)
        assertEquals(1, result.size)
        assertEquals("Hello", result[0].payload)
    }
}