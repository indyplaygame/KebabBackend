package api.indy.kebab.controller;

import api.indy.kebab.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(new ErrorResponse(e.getBindingResult().getFieldErrors().getFirst().getDefaultMessage()), HttpStatus.BAD_REQUEST);
    }
}
