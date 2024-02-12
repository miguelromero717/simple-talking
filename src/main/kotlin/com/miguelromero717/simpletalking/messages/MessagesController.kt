package com.miguelromero717.simpletalking.messages

import com.miguelromero717.simpletalking.messages.service.MessagesService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/messages")
class MessagesController(
    private val messagesService: MessagesService
) {
    
    @PostMapping("/send")
    fun sendMessage(
        @RequestHeader("sender-id") senderId: String,
        @RequestBody payload: SendMessageRequestDTO
    ) : ResponseEntity<String> {
        messagesService.sendMessage(
            senderId = senderId,
            payload = payload
        )
        return ResponseEntity.ok().body("Message sent!")
    }
}