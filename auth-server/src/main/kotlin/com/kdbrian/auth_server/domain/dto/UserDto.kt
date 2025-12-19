package com.kdbrian.auth_server.domain.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.kdbrian.auth_server.domain.model.AppUser
import kotlinx.serialization.Serializable

@Serializable
class UserDto(
    val id: Long = 0L,
    val displayName: String="",
    val email: String,
    @JsonIgnore
//    @Transient
    val textPasswordValue: String?=null,
    val dateUpdated: Long = System.currentTimeMillis()
){

    internal constructor() : this(
        0L,
        "",
        "",
        "",
        System.currentTimeMillis()
    )

    constructor(
        displayName: String,
        email: String,
        textPasswordValue: String
    ) : this(
        0L,
        displayName,
        email,
        textPasswordValue,
        System.currentTimeMillis()
    )
    constructor(
        email: String,
        textPasswordValue: String
    ) : this(
        0L,
        "",
        email,
        textPasswordValue,
        System.currentTimeMillis()
    )

    class Builder {
        var id: Long = 0L
        var email: String = ""
        var password: String? = ""
        var displayName: String = ""
        var dateUpdated: Long = System.currentTimeMillis()


        fun id(inputId: Long) = apply { id = inputId }
        fun email(inputEmail: String) = apply { email = inputEmail }
        fun password(inputPassword: String?) = apply { password = inputPassword }
        fun displayName(inputName: String) = apply { displayName = inputName }
        fun updateAt(timeMillis: Long) = apply { dateUpdated = timeMillis }

        fun build(): UserDto {

            return UserDto(
                id, displayName, email, password, dateUpdated
            )
        }
    }

    override fun toString(): String {
        return "UserDto{displayName = $displayName, email= $email, textPasswordValue= $textPasswordValue}"
    }

}

fun UserDto.toBuilder() = UserDto.Builder().apply {
    id(this@toBuilder.id)
    displayName(this@toBuilder.displayName)
    email(this@toBuilder.email)
    password(this@toBuilder.textPasswordValue.toString())
}

fun UserDto.toEntity() = AppUser(
    id = id,
    email = email,
    displayName = displayName,
    textPasswordValue = textPasswordValue.toString(),
    dateUpdated = dateUpdated
)
