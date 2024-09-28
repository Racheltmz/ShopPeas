package com.peaslimited.shoppeas.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

// Error Response Class
@Getter
@AllArgsConstructor
public class ErrorResponse {
    // Attributes
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
}