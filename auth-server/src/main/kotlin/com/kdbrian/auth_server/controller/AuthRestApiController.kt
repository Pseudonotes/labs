package com.kdbrian.auth_server.controller

import com.kdbrian.auth_server.domain.dto.UserDto
import com.kdbrian.auth_server.domain.dto.toBuilder
import com.kdbrian.auth_server.domain.dto.toEntity
import com.kdbrian.auth_server.domain.service.JwtService
import com.kdbrian.auth_server.domain.service.UserService
import com.kdbrian.auth_server.util.AuthResponse
import com.kdbrian.auth_server.util.ResponseWrapper
import com.kdbrian.auth_server.util.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthRestApiController(
    private val userService: UserService,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    val logger = logger()


    @PostMapping("/register")
    fun register(@RequestBody userDto: UserDto?): ResponseEntity<AuthResponse> {

        require(userDto != null) { "Missing fields(displayname,password, email) in body." }

        userService.addUser(userDto)
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                userDto.email,
                userDto.textPasswordValue
            )
        )
        val user = userService.getUserByEmail(userDto.email)
        if (authentication.isAuthenticated && user != null) {
            return ResponseEntity.ok(
                AuthResponse(
                    userDto = user.toBuilder().password(null).build(),
                    accessToken = jwtService.generateToken(
                        mapOf(),
                        user.toEntity()
                    )
                )
            )
        }

        return ResponseEntity(
            AuthResponse(),
            HttpStatus.UNAUTHORIZED
        )
    }

    @PostMapping("/login")
    fun login(@RequestBody userDto: UserDto): ResponseEntity<AuthResponse> {
        logger.info("body {}", userDto)

        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                userDto.email,
                userDto.textPasswordValue
            )
        )

        val userByEmail = userService.getUserByEmail(userDto.email)
        logger.info("userByEmail {}", userByEmail)

        if (authentication.isAuthenticated && userByEmail != null) {
            val principal = SecurityContextHolder.getContext().authentication?.principal
            logger.info("principal {}", principal)
            return ResponseEntity.ok(
                AuthResponse(
                    jwtService.generateToken(
                        mapOf(),
                        userByEmail.toEntity()
                    ),
                    userByEmail.toBuilder().password(null).build()
                )
            )
        }

        return ResponseEntity(
            AuthResponse(),
            HttpStatus.UNAUTHORIZED
        )


    }

    @GetMapping("/account")
    fun myAccount(
        @RequestHeader("Authorization") authHeader: String
    ): ResponseEntity<ResponseWrapper<UserDto>> {
        logger.info("first")
        val securityContext = SecurityContextHolder.getContext()
        val authentication = securityContext.authentication

        val principal = authentication?.name
        logger.info("email {} {}", principal,authentication?.isAuthenticated)

        val userByEmail = userService.getUserByEmail(principal.toString())

        require(userByEmail != null) { "Invalid details please sign In again." }

        return ResponseEntity.ok(
            ResponseWrapper(
                userByEmail.toBuilder().password(null).build(),
                success = true,
                message = "Account retrieved."

        )
        )
    }

}