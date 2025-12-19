package com.kdbrian.auth_server.data.impl

import com.kdbrian.auth_server.domain.service.JwtService
import com.kdbrian.auth_server.util.logger
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey

@Service
class JwtServiceImpl(
    @Value("\${spring.security.jwt.secret}")
    private val secret: String
) : JwtService {

    private val logger = logger()


    override fun generateToken(
        extraClaims: Map<String, Any>,
        details: UserDetails
    ): String {
        return Jwts.builder().apply {

            claims(extraClaims)
            subject(details.username)
            issuedAt(Date(System.currentTimeMillis()))
            expiration(Date(System.currentTimeMillis() + 30.times(60).times(1000)))
            signWith(getSigningKey())
        }.compact()
    }

    override fun extractUserName(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    override fun <T> extractClaim(
        token: String,
        resolver: Function<Claims, T>
    ): T {
        val claims = extractAllClaims(token)
        return resolver.apply(claims)
    }

    override fun extractAllClaims(token: String): Claims {
        return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    override fun getSigningKey(): SecretKey {
//        logger.info("secret {}", secret)
        val bytes = Decoders.BASE64.decode(secret)
        return Keys.hmacShaKeyFor(bytes)
    }

    override fun isTokenValid(
        token: String,
        userDetails: UserDetails
    ): Boolean {
        val userName = extractUserName(token)
        return userName == userDetails.username && !isTokenExpired(token)
    }

    override fun isTokenExpired(token: String): Boolean {
        return extractClaim(token, Claims::getExpiration).before(Date())
    }
}