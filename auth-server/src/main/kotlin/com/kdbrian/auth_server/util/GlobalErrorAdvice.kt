package com.kdbrian.auth_server.util

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalErrorAdvice {

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDuplicateFieldsException(
        e : DataIntegrityViolationException
    ): ResponseEntity<ResponseWrapper<String>> {
        return ResponseEntity(
            ResponseWrapper(
                success = false,
                message = if (e.message.toString().contains("unique_username")) "username is already taken." else "email already taken.",
                data = if (e.message.toString().contains("unique_username")) "username is already taken." else "email already taken.",
            ),
            HttpStatusCode.valueOf(400)
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(
        e : Exception
    ): ResponseEntity<ResponseWrapper<String>> {
        return ResponseEntity(
            ResponseWrapper(
                success = false,
                message = e.message.toString(),
                data = e.message.toString()
            ),
            HttpStatusCode.valueOf(400)
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        e: IllegalArgumentException
    ): ResponseEntity<ResponseWrapper<ErrorWrapper>> {
        return ResponseEntity(
            ResponseWrapper(
                data = ErrorWrapper(cause = e.message.toString()),
                message = e.message,
                success = false
            ),
            HttpStatusCode.valueOf(400)
        )
    }


}