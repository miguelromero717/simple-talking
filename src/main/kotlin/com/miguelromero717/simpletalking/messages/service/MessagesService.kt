package com.miguelromero717.simpletalking.messages.service

import com.miguelromero717.simpletalking.messages.Message
import com.miguelromero717.simpletalking.messages.MessageRepository
import com.miguelromero717.simpletalking.messages.SendMessageRequestDTO
import com.miguelromero717.simpletalking.shared.UserNotFoundException
import com.miguelromero717.simpletalking.users.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class MessagesService(
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository
) : IMessagesService {
    
    override fun sendMessage(
        senderId: String,
        payload: SendMessageRequestDTO
    ) {
        checkSenderNotReceiver(
            senderId = senderId,
            receiverId = payload.receiverId
        )
        
        val sender = userRepository.findByExternalId(senderId) ?: throw UserNotFoundException("Sender not found")
        val receiver = userRepository.findByExternalId(payload.receiverId) ?: throw UserNotFoundException("Receiver not found")
        
        messageRepository.saveAndFlush(
            Message(
                sender = sender,
                receiver = receiver,
                payload = payload.payload
            )
        )
    }
    
    private fun checkSenderNotReceiver(
        senderId: String,
        receiverId: String
    ) {
        if (senderId == receiverId) {
            throw IllegalArgumentException("Sender and receiver cannot be the same")
        }
    }
}
