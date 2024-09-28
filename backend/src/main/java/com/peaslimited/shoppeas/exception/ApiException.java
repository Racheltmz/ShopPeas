package com.peaslimited.shoppeas.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Error Handling
 */
@Getter
@AllArgsConstructor
public class ApiException {
    private final String message;
    private final HttpStatus status;
    private final LocalDateTime timestamp;
}