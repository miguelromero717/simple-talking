package com.miguelromero717.simpletalking.users

import com.miguelromero717.simpletalking.users.service.UsersService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito

@ExtendWith(MockitoExtension::class)
class UsersServiceUnitTest {
    
    @Mock
    private lateinit var userRepository: UserRepository
    
    @InjectMocks
    private lateinit var usersService: UsersService
    
    @Captor
    private lateinit var userCaptor: ArgumentCaptor<User>
    
    @Test
    fun `test creating user`() {
        val nickname = "user123"
        val externalId = "some-external-id"
        val user = User(nickname = nickname, externalId = externalId)
        
        Mockito.`when`(userRepository.save(userCaptor.capture())).thenReturn(user)
        
        val createdUser = usersService.createUser(nickname)
        
        assertEquals(user, createdUser)
        
        assertEquals(nickname, userCaptor.value.nickname)
        assertNotNull(userCaptor.value.externalId)
    }
}
