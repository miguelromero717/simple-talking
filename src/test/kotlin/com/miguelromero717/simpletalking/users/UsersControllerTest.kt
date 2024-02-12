package com.miguelromero717.simpletalking.users

import com.miguelromero717.simpletalking.shared.utils.ObjectMapper
import com.miguelromero717.simpletalking.users.dto.CreateUserRequestDTO
import com.miguelromero717.simpletalking.users.dto.CreateUserResponseDTO
import com.miguelromero717.simpletalking.users.service.IUsersService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
class UsersControllerTest {
    
    @Autowired
    lateinit var webApplicationContext: WebApplicationContext
    
    lateinit var mockMvc: MockMvc
    
    @MockBean
    private lateinit var userService: IUsersService
    
    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }
    
    @Test
    fun `test creating user`() {
        val user = User(
            externalId = "user123",
            nickname = "user123"
        )
        val requestDTO = CreateUserRequestDTO("user123")
        val expectedResponse = ResponseEntity.ok().body(
            CreateUserResponseDTO(
                user = user
            )
        )
        
        `when`(userService.createUser(requestDTO.nickname)).thenReturn(user)
        
        val response = mockMvc.perform(
            post("/v1/users")
                .content("""{"nickname": "user123"}""")
                .contentType("application/json")
        ).andExpect(status().isOk).andReturn().response.contentAsString
        
        val responseObj = ObjectMapper.commonMapper.readValue(response, CreateUserResponseDTO::class.java)
        
        assertEquals(expectedResponse.body?.userId, responseObj?.userId)
        assertEquals(expectedResponse.body?.nickname, responseObj?.nickname)
    }
}
