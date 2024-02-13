package com.miguelromero717.simpletalking.users

import com.miguelromero717.simpletalking.SimpleTalkingApplication
import com.miguelromero717.simpletalking.users.service.UsersService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [SimpleTalkingApplication::class])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersServiceIntegrationTest {
    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var usersService: UsersService

    @Test
    fun `test creating user`() {
        val nickname = "user123"
        val createdUser = usersService.createUser(nickname)

        val userFromDatabase = userRepository.findByExternalId(externalId = createdUser.externalId)

        assertEquals(createdUser.nickname, userFromDatabase?.nickname)
    }
}
