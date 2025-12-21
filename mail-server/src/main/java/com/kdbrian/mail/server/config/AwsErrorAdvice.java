package com.kdbrian.mail.server.config;

import com.kdbrian.mail.server.domain.dto.ResponseWrapper;
import lombok.NonNull;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.model.*;

@ControllerAdvice

 /*
    *
    *
    * InvalidRequestException
InvalidWriteOffsetException
TooManyPartsException
EncryptionTypeMismatchException
AwsServiceException
SdkClientException
S3Exception
    *
    * */

public class AwsErrorAdvice {


    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<@NonNull ResponseWrapper<String>> handleInvalidRequestException(
            InvalidRequestException e
    ) {
        return new ResponseEntity<>(
                new ResponseWrapper<>(e.getMessage(), e.getMessage(), false),
                HttpStatusCode.valueOf(400)
        );
    }


    @ExceptionHandler(InvalidWriteOffsetException.class)
    public ResponseEntity<@NonNull ResponseWrapper<String>> handleInvalidWriteOffsetException(
            InvalidWriteOffsetException e
    ) {
        return new ResponseEntity<>(
                new ResponseWrapper<>(e.getMessage(), e.getMessage(), false),
                HttpStatusCode.valueOf(400)
        );
    }


    @ExceptionHandler(TooManyPartsException.class)
    public ResponseEntity<@NonNull ResponseWrapper<String>> handleTooManyPartsException(
            TooManyPartsException e
    ) {
        return new ResponseEntity<>(
                new ResponseWrapper<>(e.getMessage(), e.getMessage(), false),
                HttpStatusCode.valueOf(400)
        );
    }


    @ExceptionHandler(EncryptionTypeMismatchException.class)
    public ResponseEntity<@NonNull ResponseWrapper<String>> handleEncryptionTypeMismatchException(
            EncryptionTypeMismatchException e
    ) {
        return new ResponseEntity<>(
                new ResponseWrapper<>(e.getMessage(), e.getMessage(), false),
                HttpStatusCode.valueOf(400)
        );
    }


    @ExceptionHandler(AwsServiceException.class)
    public ResponseEntity<@NonNull ResponseWrapper<String>> handleAwsServiceException(
            AwsServiceException e
    ) {
        return new ResponseEntity<>(
                new ResponseWrapper<>(e.getMessage(), e.getMessage(), false),
                HttpStatusCode.valueOf(400)
        );
    }


    @ExceptionHandler(SdkClientException.class)
    public ResponseEntity<@NonNull ResponseWrapper<String>> handleSdkClientException(
            SdkClientException e
    ) {
        return new ResponseEntity<>(
                new ResponseWrapper<>(e.getMessage(), e.getMessage(), false),
                HttpStatusCode.valueOf(400)
        );
    }

    @ExceptionHandler(S3Exception.class)
    public ResponseEntity<@NonNull ResponseWrapper<String>> handleS3Exception(
            S3Exception e
    ) {
        return new ResponseEntity<>(
                new ResponseWrapper<>(e.getMessage(), e.getMessage(), false),
                HttpStatusCode.valueOf(400)
        );
    }


}
