package com.miguelromero717.simpletalking.messages.async

data class MessageSchema(
    val senderNickname: String,
    val receiverNickname: String,
    val payload: String
)
