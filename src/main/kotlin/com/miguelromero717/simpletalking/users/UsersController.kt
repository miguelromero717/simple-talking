package com.miguelromero717.simpletalking.users

import com.miguelromero717.simpletalking.users.dto.CreateUserRequestDTO
import com.miguelromero717.simpletalking.users.dto.CreateUserResponseDTO
import com.miguelromero717.simpletalking.users.service.IUsersService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/users")
@Tag(name = "Users", description = "Users management APIs")
class UsersController(
    private val userService: IUsersService,
) {
    @Operation(
        summary = "Create a new user",
        description = "Create a new user",
        tags = ["users", "post"],
    )
    @PostMapping
    fun createUser(
        @RequestBody payload: CreateUserRequestDTO,
    ): ResponseEntity<CreateUserResponseDTO> {
        val newUser = userService.createUser(payload.nickname)
        return ResponseEntity.ok().body(CreateUserResponseDTO(newUser))
    }
}
