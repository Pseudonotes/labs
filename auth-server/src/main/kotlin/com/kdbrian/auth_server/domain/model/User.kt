package com.kdbrian.auth_server.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kdbrian.auth_server.domain.dto.UserDto
import jakarta.persistence.*
import kotlinx.serialization.Transient
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

interface User {
    val id: Long
    val displayName: String
    val email: String
    val textPasswordValue: String?
    val dateJoined: Long
        get() = System.currentTimeMillis()
    val dateUpdated: Long
        get() = System.currentTimeMillis()
}

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(
            columnNames = ["displayName"],
            name = "unique_username"
        ),
        UniqueConstraint(
            columnNames = ["email"],
            name = "unique_email"
        )
    ]
)
class AppUser(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override val id: Long,
    override val displayName: String,
    override val email: String,
    @Transient
    @JsonIgnore
    override val textPasswordValue: String="",
    override val dateUpdated: Long
) : User, UserDetails {


    internal constructor() : this(
        0L,
        "",
        "",
        "",
        System.currentTimeMillis()
    )

    constructor(displayName: String, email: String, password: String) : this(
        0L,
        displayName,
        email,
        password,
        System.currentTimeMillis()
    )

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return Collections.singleton(SimpleGrantedAuthority("user"))
    }

    override fun getPassword(): String? {
        return textPasswordValue
    }

    fun getTextPassword(): String? {
        return textPasswordValue
    }


    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true

    override fun toString(): String {
        return "AppUser{displayName= $displayName, email= $email, id= $id}"
    }

}

fun AppUser.toDto() = UserDto(
    id,
    displayName,
    email,
    textPasswordValue,
    dateUpdated
)