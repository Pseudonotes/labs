package com.kdbrian.auth_server.domain.service

import com.kdbrian.auth_server.domain.dto.UserDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {
    fun allUsers(pageable: Pageable) : Page<UserDto>
    fun addUser(userDto: UserDto)
    fun getUserById(id: Long): UserDto
    fun getUserByDisplayName(name: String): UserDto
    fun getUsersByDisplayName(pageable: Pageable,name: String): Page<UserDto>
    fun getUserByEmail(email: String): UserDto?
    fun updateUserById(id: Long, dto: UserDto): UserDto
    fun deleteUserById(id: Long): Boolean
}