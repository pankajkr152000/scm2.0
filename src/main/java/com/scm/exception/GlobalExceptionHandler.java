package com.scm.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scm.constants.ErrorCodes;
import com.scm.dto.ApiResponseDTO;

import lombok.extern.slf4j.Slf4j;


/**
 * Global Exception Handler for centralized error handling with logging.
 *
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Catch custom business exceptions (AppRuntimeException)</li>
 *   <li>Handle validation errors thrown by @Valid</li>
 *   <li>Provide a fallback for unexpected exceptions</li>
 *   <li>Log all exceptions for debugging and monitoring</li>
 * </ul>
 * </p>
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles custom business exceptions thrown in services/controllers.
     */
    @ExceptionHandler(AppRuntimeException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleAppException(AppRuntimeException ex) {
        ErrorCodes error = ex.getError();

        // Log error message + stack trace
        log.error("Business Exception occurred: {}", error.getMessage(), ex);

        ApiResponseDTO<Object> response = new ApiResponseDTO<>(
                "error",
                error.getMessage(),
                null
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * Handles DTO validation errors (from @Valid).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        // Log validation issues (field → error)
        log.warn("Validation failed for request: {}", errors);

        ApiResponseDTO<Map<String, String>> response = new ApiResponseDTO<>(
                "validation_error",
                "Validation failed",
                errors
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * Fallback for unhandled exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleGeneralException(Exception ex) {
        // Log stacktrace for debugging
        log.error("Unhandled exception occurred: {}", ex.getMessage(), ex);

        ApiResponseDTO<Object> response = new ApiResponseDTO<>(
                "error",
                "Something went wrong: " + ex.getMessage(),
                null
        );
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
