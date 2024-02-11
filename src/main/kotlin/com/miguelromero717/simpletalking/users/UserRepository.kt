package com.miguelromero717.simpletalking.users

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Int> {
    fun findByNickname(nickname: String): User
}
