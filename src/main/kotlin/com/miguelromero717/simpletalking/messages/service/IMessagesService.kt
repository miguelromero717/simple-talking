package com.miguelromero717.simpletalking.messages.service

import com.miguelromero717.simpletalking.messages.SendMessageRequestDTO

interface IMessagesService {
    
    fun sendMessage(senderId: String, payload: SendMessageRequestDTO)
}