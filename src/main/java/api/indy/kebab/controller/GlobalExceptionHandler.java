package api.indy.kebab.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global exception handler for the application.
 * Provides centralized handling of exceptions across all controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions thrown when method arguments fail validation.
     *
     * <p>Collects validation errors and returns them in a structured format.</p>
     *
     * @param e the {@link MethodArgumentNotValidException} containing validation errors.
     * @return a {@link ResponseEntity} containing a map of field errors and their messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, List<String>> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            errors.computeIfAbsent(field, k -> new java.util.ArrayList<>()).add(errorMessage);
        });

        return new ResponseEntity<>(Map.of(
            "errors", errors
        ), HttpStatus.BAD_REQUEST);
    }
}
