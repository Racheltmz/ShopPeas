package com.peaslimited.shoppeas.exception;

import com.google.firebase.auth.FirebaseAuthException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

// Handle errors thrown by controllers
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LogManager.getLogger(ApiExceptionHandler.class);

    // Handle firebase authentication errors
    @ExceptionHandler(FirebaseAuthException.class)
    public ResponseEntity<Object> handleBadRequestException(FirebaseAuthException exception) {
        // Response Status
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        // Response Body
        ApiException errorResponse = new ApiException(exception.getMessage(), badRequest, LocalDateTime.now());
        // Log error
        log.error("{} Error due to unsupported request: {}", badRequest, exception.getMessage());
        // Return response entity
        return new ResponseEntity<>(errorResponse, badRequest);
    }

    // Handle not found errors
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNotFoundException(NullPointerException exception) {
        // Response Status
        HttpStatus badRequest = HttpStatus.NOT_FOUND;
        // Response Body
        ApiException errorResponse = new ApiException(exception.getMessage(), badRequest, LocalDateTime.now());
        // Log error
        log.error("{} Record does not exist: {}", badRequest, exception.getMessage());
        // Return response entity
        return new ResponseEntity<>(errorResponse, badRequest);
    }

    // Handle uncaught errors and return response status 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaughtException(Exception exception) {
        // Response Status
        HttpStatus internalServer = HttpStatus.INTERNAL_SERVER_ERROR;
        // Response Body
        ApiException errorResponse = new ApiException(exception.getMessage(), internalServer, LocalDateTime.now());
        // Log error
        log.error("Error occurred: {}", exception.getMessage());
        // Return response entity
        return new ResponseEntity<>(errorResponse, internalServer);
    }

}

//IOException
//ExecutionException