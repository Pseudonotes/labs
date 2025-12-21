package com.kdbrian.mail.server.config;

import com.kdbrian.mail.server.domain.dto.ResponseWrapper;
import jakarta.mail.MessagingException;
import lombok.NonNull;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorAdvice {


    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<@NonNull ResponseWrapper<String>> handleMessagingException(
            MessagingException e
    ) {
        return new ResponseEntity<>(
                new ResponseWrapper<>(e.getMessage(), e.getMessage(), false),
                HttpStatusCode.valueOf(400)
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<@NonNull ResponseWrapper<String>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e
    ) {
        return new ResponseEntity<>(
                new ResponseWrapper<>(e.getMessage(), e.getMessage(), false),
                HttpStatusCode.valueOf(400)
        );
    }





}
