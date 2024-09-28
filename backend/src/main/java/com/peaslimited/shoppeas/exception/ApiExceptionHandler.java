package com.peaslimited.shoppeas.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

// Handle errors thrown by controllers
@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LogManager.getLogger(ExceptionHandler.class);

    // Handle errors caused by requests that are not supported
    // Override handleExceptionInternal method to return customised ErrorResponse body
    @Override
    public ResponseEntity<Object> handleExceptionInternal(
            Exception exception,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        // Response Body
        ErrorResponse errorResponse = new ErrorResponse(status, exception.getMessage(), LocalDateTime.now());
        // Log error
        log.error("{} Error due to unsupported requests: {}", status, exception.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

    // Handle uncaught errors and return response status 500
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtException(
            Exception exception
    ) {
        // Response Status
        HttpStatus internalServer = HttpStatus.INTERNAL_SERVER_ERROR;
        // Response Body
        ErrorResponse errorResponse = new ErrorResponse(internalServer, exception.getMessage(), LocalDateTime.now());
        // Log error
        log.error("Error occurred: {}", exception.getMessage());
        return new ResponseEntity<>(errorResponse, internalServer);
    }

}