package com.kdbrian.auth_server.util

data class ErrorWrapper(
    val cause : String,
    val trace : String?=null
)
