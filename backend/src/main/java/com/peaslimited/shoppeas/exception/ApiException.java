package com.peaslimited.shoppeas.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * A custom exception class to represent API-related errors.
 * This class encapsulates information about the error message, HTTP status,
 * and the timestamp when the exception occurred. Lombok annotations are used to generate a
 * getter method for each field and the constructor to initialize the fields.
 */
@Getter
@AllArgsConstructor
public class ApiException {
    /**
     * The error message describing the exception or issue.
     */
    private final String message;
    /**
     * The HTTP status associated with the error (e.g., 404 Not Found, 500 Internal Server Error).
     */
    private final HttpStatus status;
    /**
     * The timestamp when the exception occurred, used for logging or debugging.
     */
    private final LocalDateTime timestamp;
}