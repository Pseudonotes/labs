package com.kdbrian.auth_server.data.repo

import com.kdbrian.auth_server.domain.model.AppUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<AppUser, Long> {

    fun findUserByDisplayName(name:String): Optional<AppUser>

    fun findUsersByDisplayNameContaining(pageable: Pageable,name:String): Page<AppUser>

    fun findUserByEmail(email : String) : Optional<AppUser>


//    fun updateUserById(id: String, dto: UserDto): AppUser



}