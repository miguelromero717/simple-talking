package com.miguelromero717.simpletalking.messages.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.miguelromero717.simpletalking.messages.Message
import java.time.Instant

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class MessageDTO(
    val senderNickname: String,
    val receiverNickname: String,
    val payload: String,
    val sentAt: Instant
) {
    constructor(message: Message): this(
        senderNickname = message.sender.nickname,
        receiverNickname = message.receiver.nickname,
        payload = message.payload,
        sentAt = message.createdAt
    )
}
