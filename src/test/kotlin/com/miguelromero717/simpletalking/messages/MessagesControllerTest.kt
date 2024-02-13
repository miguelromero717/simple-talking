package com.miguelromero717.simpletalking.messages

import com.fasterxml.jackson.core.type.TypeReference
import com.miguelromero717.simpletalking.messages.dto.MessageDTO
import com.miguelromero717.simpletalking.messages.dto.SendMessageRequestDTO
import com.miguelromero717.simpletalking.messages.service.MessagesService
import com.miguelromero717.simpletalking.shared.utils.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.time.Instant
import java.util.UUID

@SpringBootTest
class MessagesControllerTest {
    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var messagesService: MessagesService

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun `test sending message`() {
        // given
        val userId = UUID.randomUUID().toString()
        val receiverId = UUID.randomUUID().toString()
        val payload = "Hello, World!"

        val requestDTO =
            SendMessageRequestDTO(
                payload = payload,
                receiverId = receiverId,
            )
        val expectedResponse = ResponseEntity.ok().body("Message sent!")

        `when`(messagesService.sendMessage(userId, requestDTO)).then { }

        // when
        val response =
            mockMvc.perform(
                post("/v1/messages/send")
                    .header("user-id", userId)
                    .content("""{"receiver_id": "receiver1", "payload": "$payload"}""")
                    .contentType("application/json"),
            ).andExpect(status().isOk).andReturn().response.contentAsString

        // then
        assertEquals(expectedResponse.body, response)
    }

    @Test
    fun `test getting received messages`() {
        val userId = "user123"
        val messageDTOList =
            listOf(
                MessageDTO(
                    senderNickname = "sender1",
                    receiverNickname = "receiver1",
                    payload = "Hello!",
                    sentAt = Instant.now(),
                ),
                MessageDTO(
                    senderNickname = "sender2",
                    receiverNickname = "receiver1",
                    payload = "Hello!",
                    sentAt = Instant.now(),
                ),
            )

        `when`(messagesService.getMessagesReceived(userId)).thenReturn(messageDTOList)

        val response =
            mockMvc.perform(
                get("/v1/messages/received")
                    .header("user-id", userId),
            ).andExpect(status().isOk).andReturn().response.contentAsString

        val responseObjects = ObjectMapper.commonMapper.readValue(response, object : TypeReference<List<MessageDTO?>?>() {})
        assertTrue(responseObjects?.isNotEmpty() == true)
        assertEquals(2, responseObjects?.size)
        assertTrue(responseObjects?.all { it?.receiverNickname == "receiver1" } == true)
        assertTrue(responseObjects?.any { it?.senderNickname == "sender1" } == true)
        assertTrue(responseObjects?.any { it?.senderNickname == "sender2" } == true)
    }

    @Test
    fun `test getting sent messages`() {
        val userId = "user123"
        val messageDTOList =
            listOf(
                MessageDTO(
                    senderNickname = "sender1",
                    receiverNickname = "receiver1",
                    payload = "Hello!",
                    sentAt = Instant.now(),
                ),
                MessageDTO(
                    senderNickname = "sender1",
                    receiverNickname = "receiver2",
                    payload = "Hello!",
                    sentAt = Instant.now(),
                ),
            )

        `when`(messagesService.getMessagesSent(userId)).thenReturn(messageDTOList)

        val response =
            mockMvc.perform(
                get("/v1/messages/sent")
                    .header("user-id", userId),
            ).andExpect(status().isOk).andReturn().response.contentAsString

        val responseObjects = ObjectMapper.commonMapper.readValue(response, object : TypeReference<List<MessageDTO?>?>() {})
        assertTrue(responseObjects?.isNotEmpty() == true)
        assertEquals(2, responseObjects?.size)
        assertTrue(responseObjects?.all { it?.senderNickname == "sender1" } == true)
        assertTrue(responseObjects?.any { it?.receiverNickname == "receiver1" } == true)
    }

    @Test
    fun `test getting received messages by specific user`() {
        val userId = "user123"
        val senderId = "sender456"
        val messageDTOList =
            listOf(
                MessageDTO(
                    senderNickname = "sender1",
                    receiverNickname = "receiver1",
                    payload = "Hello!",
                    sentAt = Instant.now(),
                ),
                MessageDTO(
                    senderNickname = "sender2",
                    receiverNickname = "receiver1",
                    payload = "Hello!",
                    sentAt = Instant.now(),
                ),
            )

        `when`(messagesService.getMessagesReceivedFromSpecificSender(userId, senderId)).thenReturn(messageDTOList)

        val response =
            mockMvc.perform(
                get("/v1/messages/received/by-user?senderId=$senderId")
                    .header("user-id", userId),
            ).andExpect(status().isOk).andReturn().response.contentAsString

        val responseObjects = ObjectMapper.commonMapper.readValue(response, object : TypeReference<List<MessageDTO?>?>() {})

        assertTrue(responseObjects?.isNotEmpty() == true)
        assertEquals(2, responseObjects?.size)
        assertTrue(responseObjects?.all { it?.receiverNickname == "receiver1" } == true)
        assertTrue(responseObjects?.any { it?.senderNickname == "sender1" } == true)
        assertTrue(responseObjects?.any { it?.senderNickname == "sender2" } == true)
    }
}
