package com.kdbrian.auth_server.util

import com.kdbrian.auth_server.domain.dto.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val accessToken : String? = null,
    val userDto: UserDto? = null
)
