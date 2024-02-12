package com.miguelromero717.simpletalking.users.dto

import com.miguelromero717.simpletalking.users.User

data class CreateUserRequestDTO(
    val nickname: String
) {
    constructor(user: User) : this(
        nickname = user.nickname
    )
}
