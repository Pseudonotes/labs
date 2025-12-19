package com.kdbrian.auth_server.data.impl

import com.kdbrian.auth_server.data.repo.UserRepository
import com.kdbrian.auth_server.domain.dto.UserDto
import com.kdbrian.auth_server.domain.dto.toBuilder
import com.kdbrian.auth_server.domain.dto.toEntity
import com.kdbrian.auth_server.domain.model.toDto
import com.kdbrian.auth_server.domain.service.UserService
import com.kdbrian.auth_server.util.logger
import lombok.extern.slf4j.Slf4j
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
@Slf4j
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {
    private val logger = logger()

    override fun allUsers(pageable: Pageable): Page<UserDto> {
        return userRepository.findAll(pageable).map { it.toDto() }
    }


    override fun addUser(userDto: UserDto) {
        userDto.run {
            require(displayName.isNotEmpty()) { "displayName cannot be blank." }
            require(email.isNotEmpty()) { "email cannot be blank." }
            require(textPasswordValue?.isNotEmpty() == true) { "textPasswordValue cannot be blank." }
        }

        logger.info("pwdd {}", userDto.textPasswordValue)
        val inputPassword = passwordEncoder.encode(userDto.textPasswordValue?.trim()).toString()
        logger.info("pwd in {}", inputPassword)



        userRepository.save(
            userDto
                .toBuilder()
                .password(inputPassword)
                .build()
                .toEntity()
        )
    }

    override fun getUserById(id: Long): UserDto {
        val optional = userRepository.findById(id)
        require(optional.isPresent) { "Invalid user by id $id." }
        return optional.get().toDto()
    }

    override fun getUserByDisplayName(name: String): UserDto {
        val optional = userRepository.findUserByDisplayName(name)
        require(optional.isPresent) { "Invalid user by id $name." }
        return optional.get().toDto()
    }

    override fun getUsersByDisplayName(pageable: Pageable,name: String) = userRepository.findUsersByDisplayNameContaining(pageable,name).map { it.toDto() }

    override fun getUserByEmail(email: String): UserDto? {
        val optional = userRepository.findUserByEmail(email)
        return when (optional.isPresent) {
            true -> optional.get().toDto()
            else -> null
        }
    }

    override fun updateUserById(
        id: Long,
        dto: UserDto
    ): UserDto {
        val user = getUserById(id)
        val updates = user.toBuilder()

        if (user.displayName != dto.displayName)
            updates.displayName(dto.displayName)

        val appUser = userRepository.save(
            updates.build().toEntity()
        )


        return appUser.toDto()

    }

    override fun deleteUserById(id: Long): Boolean {
        getUserById(id)
        userRepository.deleteById(id)
        return true
    }
}