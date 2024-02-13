package com.miguelromero717.simpletalking.messages.async

data class MessageSchema(
    val senderId: String,
    val receiverId: String,
    val payload: String,
) {
    constructor() : this(
        senderId = "",
        receiverId = "",
        payload = "",
    )
}
