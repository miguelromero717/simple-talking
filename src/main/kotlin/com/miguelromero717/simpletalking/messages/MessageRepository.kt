package com.miguelromero717.simpletalking.messages

import com.miguelromero717.simpletalking.users.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : JpaRepository<Message, Int> {
    fun findByReceiver(receiver: User): List<Message>
    fun findBySender(sender: User): List<Message>
}
