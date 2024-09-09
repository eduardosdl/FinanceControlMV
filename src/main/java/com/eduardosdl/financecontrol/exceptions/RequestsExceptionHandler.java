package com.eduardosdl.financecontrol.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RequestsExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleError400(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getFieldErrors().stream()
                .map(fieldError -> String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        if (errorMessages.isEmpty()) {
            errorMessages.add("Erro de validação desconhecido");
        }

        return ResponseEntity.badRequest().body(new ControllerErrorResponse(errorMessages));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handlerValidationException(ValidationException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
