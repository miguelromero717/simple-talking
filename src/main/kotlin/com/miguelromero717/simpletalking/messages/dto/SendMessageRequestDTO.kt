package com.miguelromero717.simpletalking.messages.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class SendMessageRequestDTO(
    val receiverId: String,
    val payload: String,
)
