package com.kdbrian.auth_server.config.security

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
class SecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val authenticationFilter: AuthenticationFilter
) {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder(8)


    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        return DaoAuthenticationProvider(userDetailsService).apply {
            setPasswordEncoder(passwordEncoder())
        }
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager? {
        return config.authenticationManager
    }


    @Bean
    fun securityFilterChain(
        security: HttpSecurity
    ): DefaultSecurityFilterChain? {
        return security
            .csrf { configurer -> configurer.disable() }
            .cors { configurer -> configurer.disable() }
            .authorizeHttpRequests { req ->
                req
                    .requestMatchers("/api/**", "/open-api-spec.yaml", "/docs").permitAll()
                    .requestMatchers("/auth/register", "/auth/login", "/auth/reset-password").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }


}