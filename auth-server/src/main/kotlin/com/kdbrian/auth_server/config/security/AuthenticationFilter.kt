package com.kdbrian.auth_server.config.security

import com.kdbrian.auth_server.domain.service.JwtService
import com.kdbrian.auth_server.util.logger
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class AuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService : UserDetailsService
): OncePerRequestFilter(){


    private val logger = logger()

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        logger.info("second")
        val authHeader = request.getHeader("Authorization")

        logger.info("auth : $authHeader")

        //check token exists
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.substring(7)
        //validate token against user info
        val username: String = jwtService.extractUserName(token)

        logger.info("user $username")

        val securityContext: SecurityContext = SecurityContextHolder.getContext()

        if (securityContext.authentication == null) {
            //fetching user from db

            val userDetails: UserDetails = userDetailsService.loadUserByUsername(username)

            //validate token
            val tokenValid = jwtService.isTokenValid(token, userDetails)
            logger.info("tokenValid {} for {}", tokenValid, userDetails)
            if (tokenValid) {
                //update context

                val utoken = UsernamePasswordAuthenticationToken(
                    userDetails.username,
                    userDetails.password,
                    userDetails.authorities
                )

                utoken.details = WebAuthenticationDetailsSource().buildDetails(request)
                securityContext.authentication = utoken
                logger.info("token $utoken")
            }
        }

        //pass filter to next
        filterChain.doFilter(request, response)
    }
}