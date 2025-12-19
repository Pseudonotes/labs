package com.kdbrian.auth_server.controller

import com.kdbrian.auth_server.domain.dto.UserDto
import com.kdbrian.auth_server.domain.service.UserService
import com.kdbrian.auth_server.util.ResponseWrapper
import com.kdbrian.auth_server.util.logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/auth/users")
@RestController
class UsersRestApiController(
    private val userService: UserService
) {

    private val logger = logger()

    @GetMapping
    fun allUsers(
        @RequestParam(required = false, name = "pages", defaultValue = "20")
        pages: Int,
        @RequestParam(name = "name", required = false)
        name: String,
    ): ResponseEntity<ResponseWrapper<Page<UserDto>>> {

        require(pages in 5..20) { "Page limit is 5-20" }

        if (name.isNotEmpty()) {
            return ResponseEntity.ok(
                ResponseWrapper(
                    userService.getUsersByDisplayName(Pageable.ofSize(pages), name),
                    message = "Retrieved user with name $name"
                )
            )
        } else
            return ResponseEntity.ok(
                ResponseWrapper(
                    userService.allUsers(
                        Pageable.ofSize(pages)
                    )
                )
            )
    }

    @PostMapping("/create")
    fun createUser(
        @RequestBody userDto: UserDto
    ): ResponseEntity<ResponseWrapper<String>> {

        logger.info("Create {}", userDto)
        userService.addUser(userDto)

        return ResponseEntity.ok(
            ResponseWrapper(
                data = "Account created successfully."
            )
        )
    }

    @GetMapping("/{id}")
    fun userById(
        @PathVariable(name = "id")
        id: Long
    ): ResponseEntity<ResponseWrapper<UserDto>> {
        return ResponseEntity.ok(
            ResponseWrapper(
                userService.getUserById(id)
            )
        )
    }


    @GetMapping("/user/{name}")
    fun userByDiplayName(
        @PathVariable(name = "name")
        name: String
    ): ResponseEntity<ResponseWrapper<UserDto>> {
        return ResponseEntity.ok(
            ResponseWrapper(
                userService.getUserByDisplayName(name)
            )
        )
    }



}