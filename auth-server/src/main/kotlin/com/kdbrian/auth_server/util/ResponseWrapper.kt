package com.kdbrian.auth_server.util

data class ResponseWrapper<T>(
    val data: T,
    val message: String? = null,
    val success: Boolean = false
)
