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
/**
 * Global exception handler for managing errors across all controllers which
 * logs the errors using Log4j2 for better traceability and debugging.
 * The handler manages errors like: firebase authentication errors (e.g., invalid credentials),
 * not found errors (e.g., when a resource is not found),
 * and uncaught exceptions (e.g., unexpected errors in the application)
 */
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LogManager.getLogger(ApiExceptionHandler.class);

    // Handle firebase authentication errors
    /**
     * Handles Firebase authentication errors (e.g., invalid token, authentication failure) and
     * returns a structured {@link ApiException} response with a {@link HttpStatus#BAD_REQUEST}
     * status code and logs the error details.
     * @param exception the {@link FirebaseAuthException} thrown during Firebase authentication
     * @return a {@link ResponseEntity} containing the error details and HTTP status
     */
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
    /**
     * Handles {@link NullPointerException} to manage resource not found errors and
     * returns a structured {@link ApiException} response with a {@link HttpStatus#NOT_FOUND}
     * status code, indicating that the requested resource could not be found.
     * @param exception the {@link NullPointerException} thrown when a resource does not exist
     * @return a {@link ResponseEntity} containing the error details and HTTP status
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNotFoundException(NullPointerException exception) {
        // Response Status
        HttpStatus badRequest = HttpStatus.NOT_FOUND;
        // Response Body
        ApiException errorResponse = new ApiException(exception.getMessage(), badRequest, LocalDateTime.now());
        // Log error
        log.error("{} Record does not exist: {}. {}", badRequest, exception.getMessage(), exception.getStackTrace());
        // Return response entity
        return new ResponseEntity<>(errorResponse, badRequest);
    }

    // Handle uncaught errors and return response status 500
    /**
     * Handles uncaught exceptions that are not specifically handled by the other methods in this class,
     * and returns a generic {@link ApiException} response with a {@link HttpStatus#INTERNAL_SERVER_ERROR}.
     * @param exception the uncaught {@link Exception} that was thrown during processing
     * @return a {@link ResponseEntity} containing the error details and HTTP status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaughtException(Exception exception) {
        // Response Status
        HttpStatus internalServer = HttpStatus.INTERNAL_SERVER_ERROR;
        // Response Body
        ApiException errorResponse = new ApiException(exception.getMessage(), internalServer, LocalDateTime.now());
        // Log error
        log.error("Error occurred: {}, {}", exception.getMessage(), exception.getStackTrace());
        // Return response entity
        return new ResponseEntity<>(errorResponse, internalServer);
    }

}
