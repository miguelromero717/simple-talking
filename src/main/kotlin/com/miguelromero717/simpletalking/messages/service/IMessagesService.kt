package com.miguelromero717.simpletalking.messages.service

import com.miguelromero717.simpletalking.messages.async.MessageSchema
import com.miguelromero717.simpletalking.messages.dto.MessageDTO
import com.miguelromero717.simpletalking.messages.dto.SendMessageRequestDTO

interface IMessagesService {
    
    fun sendMessage(senderId: String, payload: SendMessageRequestDTO)
    
    fun processMessage(payload: MessageSchema)
    
    fun getMessagesReceived(userId: String): List<MessageDTO>
    
    fun getMessagesSent(userId: String): List<MessageDTO>
    
    fun getMessagesReceivedFromSpecificSender(receiverId: String, senderId: String): List<MessageDTO>
}