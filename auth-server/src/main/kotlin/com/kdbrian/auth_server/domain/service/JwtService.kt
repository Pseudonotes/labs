package com.kdbrian.auth_server.domain.service

import io.jsonwebtoken.Claims
import org.springframework.security.core.userdetails.UserDetails
import java.util.function.Function
import javax.crypto.SecretKey


interface JwtService {

    fun generateToken(
        extraClaims: Map<String, Any>,
        details: UserDetails
    ): String


    fun extractUserName(
        token: String
    ): String


    fun <T> extractClaim(
        token: String,
        resolver: Function<Claims, T>
    ): T


    fun extractAllClaims(token: String): Claims

    fun getSigningKey(): SecretKey

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean

    fun isTokenExpired(token: String): Boolean


}