package com.scm.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.scm.constants.ErrorCodes;
import com.scm.dto.ApiResponseDTO;

import jakarta.servlet.http.HttpServletRequest;
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
@RestControllerAdvice(basePackages = "com.scm.api")
@Slf4j
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

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<?> handleNoResource(NoResourceFoundException ex) {
        // log as DEBUG, not ERROR
        log.debug("Static resource not found: {}", ex.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        return ResponseEntity
                .badRequest()
                .body("Image size must be less than 2MB");
    }

    @ExceptionHandler(UnexpectedRollbackException.class)
    public String handleTxException(
            UnexpectedRollbackException ex,
            Model model
    ) {
        model.addAttribute("message", "Transaction failed. Please retry.");
        return "error";
    }

    // @ExceptionHandler(Exception.class)
    // public String handleGeneralException(
    //         Exception ex,
    //         Model model
    // ) {
    //     model.addAttribute("message", ex.getMessage());
    //     return "error";
    // }

    @ExceptionHandler(Exception.class)
    public Object handleException(
            Exception ex,
            HttpServletRequest request,
            Model model) {

        if (request.getRequestURI().startsWith("/api")) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", ex.getMessage()));
        }

        model.addAttribute("errorMessage", ex.getMessage());
        return "error/error-page";
    }
}


