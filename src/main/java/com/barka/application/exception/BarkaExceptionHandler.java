package com.barka.application.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;


@ControllerAdvice
@Slf4j
public class BarkaExceptionHandler {

    @ExceptionHandler(value = {AccessException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorMessage> accountExceptionHandler(RuntimeException e) {
        log.error("accessExceptionHandler [{}]", e.getMessage(), e);
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(e.getMessage())
                .description("UNAUTHORIZED_ERROR")
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .timestamp(new Date()).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ErrorMessage> accountExceptionHandler(AccountNotFoundException e) {
        log.error("accountExceptionHandler [{}]", e.getMessage(), e);
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(e.getMessage())
                .description("NOT_FOUND_ERROR")
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date()).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception e) {
        log.error("globalExceptionHandler [{}]", e.getMessage(), e);
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(e.getMessage())
                .description("INTERNAL_SERVER_ERROR")
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timestamp(new Date()).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException e) {
        log.error("handleValidationException [{}]", e.getMessage());
        StringBuilder msg = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            msg.append("[FieldName:-").append(fieldName).append(", ");
            msg.append("ErrorMessage:-").append(errorMessage).append("]");
        });

        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(msg.toString())
                .description("VALIDATION_ERROR")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date()).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}
