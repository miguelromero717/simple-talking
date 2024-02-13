package com.miguelromero717.simpletalking.users.service

import com.miguelromero717.simpletalking.users.User

interface IUsersService {
    fun createUser(nickname: String): User
}
