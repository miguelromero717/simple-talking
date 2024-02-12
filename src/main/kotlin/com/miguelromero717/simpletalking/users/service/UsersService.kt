package com.miguelromero717.simpletalking.users.service

import com.miguelromero717.simpletalking.users.User
import com.miguelromero717.simpletalking.users.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class UsersService(
    private val userRepository: UserRepository
) : IUsersService {
    
    override fun createUser(nickname: String): User {
        val user = User(
            nickname = nickname,
            externalId = UUID.randomUUID().toString()
        )
        return userRepository.save(user)
    }
}
