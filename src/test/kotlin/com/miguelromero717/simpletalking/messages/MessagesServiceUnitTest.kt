package com.miguelromero717.simpletalking.messages

import com.miguelromero717.simpletalking.messages.async.MessageSchema
import com.miguelromero717.simpletalking.messages.async.MessagesProducer
import com.miguelromero717.simpletalking.messages.service.MessagesService
import com.miguelromero717.simpletalking.users.User
import com.miguelromero717.simpletalking.users.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class MessagesServiceUnitTest {
    
    @Mock
    private lateinit var messageRepository: MessageRepository
    
    @Mock
    private lateinit var userRepository: UserRepository
    
    @Mock
    private lateinit var messagesProducer: MessagesProducer
    
    @InjectMocks
    private lateinit var messagesService: MessagesService
    
    @Test
    fun `test process message`() {
        val senderId = "senderId"
        val receiverId = "receiverId"
        val payload = MessageSchema(senderId, receiverId, "Hello, World!")
        
        val sender = User(nickname = UUID.randomUUID().toString(), externalId = senderId)
        val receiver = User(nickname = UUID.randomUUID().toString(), externalId = receiverId)
        
        whenever(userRepository.findByExternalId(senderId)).thenReturn(sender)
        whenever(userRepository.findByExternalId(receiverId)).thenReturn(receiver)
        
        messagesService.processMessage(payload)
        
        verify(messageRepository).saveAndFlush(any())
    }
    
    @Test
    fun `test get messages received`() {
        val userId = "userId"
        val user = User(nickname = UUID.randomUUID().toString(), externalId = userId)
        val messages = listOf(Message(sender = user, receiver = user, payload = "Hello"))
        
        whenever(userRepository.findByExternalId(userId)).thenReturn(user)
        whenever(messageRepository.findByReceiver(user)).thenReturn(messages)
        
        val result = messagesService.getMessagesReceived(userId)
        
        assert(result.size == 1)
        assert(result[0].payload == "Hello")
    }
    
    @Test
    fun `test get messages sent`() {
        val userId = "userId"
        val user = User(nickname = UUID.randomUUID().toString(), externalId = userId)
        val messages = listOf(Message(sender = user, receiver = user, payload = "Hello"))
        
        whenever(userRepository.findByExternalId(userId)).thenReturn(user)
        whenever(messageRepository.findBySender(user)).thenReturn(messages)
        
        val result = messagesService.getMessagesSent(userId)
        
        assert(result.size == 1)
        assert(result[0].payload == "Hello")
    }
    
    @Test
    fun `test get messages received from specific sender`() {
        val receiverId = "receiverId"
        val senderId = "senderId"
        val receiver = User(nickname = UUID.randomUUID().toString(), externalId = receiverId)
        val sender = User(nickname = UUID.randomUUID().toString(), externalId = senderId)
        val messages = listOf(Message(sender = sender, receiver = receiver, payload = "Hello"))
        
        whenever(userRepository.findByExternalId(receiverId)).thenReturn(receiver)
        whenever(userRepository.findByExternalId(senderId)).thenReturn(sender)
        whenever(messageRepository.findByReceiverAndSender(receiver, sender)).thenReturn(messages)
        
        val result = messagesService.getMessagesReceivedFromSpecificSender(receiverId, senderId)
        
        assert(result.size == 1)
        assert(result[0].payload == "Hello")
    }
}
