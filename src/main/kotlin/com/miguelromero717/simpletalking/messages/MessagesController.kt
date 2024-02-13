package com.miguelromero717.simpletalking.messages

import com.miguelromero717.simpletalking.messages.dto.MessageDTO
import com.miguelromero717.simpletalking.messages.dto.SendMessageRequestDTO
import com.miguelromero717.simpletalking.messages.service.MessagesService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/messages")
@Tag(name = "Messages", description = "Messages management APIs")
class MessagesController(
    private val messagesService: MessagesService,
) {
    @Operation(
        summary = "Send a message from user A to user B",
        description =
            "Send a message from user A to user B, where A is the sender and B is the receiver. " +
                "The receiver id should come in the header user-id.",
        tags = ["messages", "post"],
    )
    @PostMapping("/send")
    fun sendMessage(
        @RequestHeader("user-id") userId: String,
        @RequestBody payload: SendMessageRequestDTO,
    ): ResponseEntity<String> {
        messagesService.sendMessage(
            senderId = userId,
            payload = payload,
        )
        return ResponseEntity.ok().body("Message sent!")
    }

    @Operation(
        summary = "Return all messages received by user A",
        description =
            "Return all messages received by user A, where A is the receiver. " +
                "The receiver id should come in the header user-id.",
        tags = ["messages", "get"],
    )
    @GetMapping("/received")
    fun getMessagesReceived(
        @RequestHeader("user-id") userId: String,
    ): ResponseEntity<List<MessageDTO>> {
        val messages = messagesService.getMessagesReceived(userId = userId)
        return ResponseEntity.ok(messages)
    }

    @Operation(
        summary = "Return all messages sent by user A",
        description =
            "Return all messages sent by user A, where A is the sender. " +
                "The sender id should come in the header user-id.",
        tags = ["messages", "get"],
    )
    @GetMapping("/sent")
    fun getMessagesSent(
        @RequestHeader("user-id") userId: String,
    ): ResponseEntity<List<MessageDTO>> {
        val messages = messagesService.getMessagesSent(userId = userId)
        return ResponseEntity.ok(messages)
    }

    @Operation(
        summary = "Return all messages received by user A from user B",
        description =
            "Return all messages received by user A from user B, where A is the receiver and B is the sender. " +
                "The receiver id should come in the header user-id.",
        tags = ["messages", "get"],
    )
    @GetMapping("/received/by-user")
    fun getMessagesReceivedBySpecificUser(
        @RequestHeader("user-id") userId: String,
        @RequestParam("senderId") senderId: String,
    ): ResponseEntity<List<MessageDTO>> {
        val messages =
            messagesService.getMessagesReceivedFromSpecificSender(
                receiverId = userId,
                senderId = senderId,
            )

        return ResponseEntity.ok(messages)
    }
}
