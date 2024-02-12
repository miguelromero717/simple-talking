package com.miguelromero717.simpletalking.messages.service

import com.miguelromero717.simpletalking.messages.Message
import com.miguelromero717.simpletalking.messages.MessageRepository
import com.miguelromero717.simpletalking.messages.dto.MessageDTO
import com.miguelromero717.simpletalking.messages.dto.SendMessageRequestDTO
import com.miguelromero717.simpletalking.shared.UserNotFoundException
import com.miguelromero717.simpletalking.users.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
    
    @Transactional(readOnly = true)
    override fun getMessagesReceived(userId: String): List<MessageDTO> {
        val receiver = userRepository.findByExternalId(externalId = userId) ?: throw UserNotFoundException("Receiver not found")
        val messagesReceivedByUser = messageRepository.findByReceiver(receiver = receiver)
        
        return buildListMessagesDTOResponse(messages = messagesReceivedByUser)
    }
    
    @Transactional(readOnly = true)
    override fun getMessagesSent(userId: String): List<MessageDTO> {
        val sender = userRepository.findByExternalId(externalId = userId) ?: throw UserNotFoundException("Sender not found")
        val messagesSentByUser = messageRepository.findBySender(sender = sender)
        
        return buildListMessagesDTOResponse(messages = messagesSentByUser)
    }

    @Transactional(readOnly = true)
    override fun getMessagesReceivedFromSpecificSender(receiverId: String, senderId: String): List<MessageDTO> {
        val receiver = userRepository.findByExternalId(externalId = receiverId) ?: throw UserNotFoundException("Receiver not found")
        val sender = userRepository.findByExternalId(externalId = senderId) ?: throw UserNotFoundException("Sender not found")
        
        val messagesReceivedBySpecificUser = messageRepository.findByReceiverAndSender(
            receiver = receiver,
            sender = sender
        )
        
        return buildListMessagesDTOResponse(messages = messagesReceivedBySpecificUser)
    }
    
    private fun buildListMessagesDTOResponse(messages: List<Message>): List<MessageDTO> {
        return messages.map { message ->
            MessageDTO(message = message)
        }
    }
}
