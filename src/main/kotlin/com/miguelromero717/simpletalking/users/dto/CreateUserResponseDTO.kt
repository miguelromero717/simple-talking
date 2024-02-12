package com.miguelromero717.simpletalking.users.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.miguelromero717.simpletalking.users.User

@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy::class)
data class CreateUserResponseDTO(
    val nickname: String,
    val userId: String
) {
    constructor(user: User): this(
        nickname = user.nickname,
        userId = user.externalId
    )
}
