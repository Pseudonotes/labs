package com.kdbrian.auth_server.config

import com.kdbrian.auth_server.data.repo.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class AppConfig(
    private val userRepository: UserRepository
) {



    @Bean
    fun userDetailsService() : UserDetailsService = UserDetailsService { username -> userRepository.findUserByEmail(username).get() }



}