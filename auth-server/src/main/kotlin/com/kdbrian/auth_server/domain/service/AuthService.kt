package com.kdbrian.auth_server.domain.service

import com.kdbrian.auth_server.domain.dto.UserDto

interface AuthService {
    fun registerUser(userDto: UserDto)
}