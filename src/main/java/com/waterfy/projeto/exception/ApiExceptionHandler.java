package com.waterfy.projeto.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(
            ApiRequestException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = TaskNotFoundException.class)
    public ResponseEntity<Object> handleApiRequestException(
            TaskNotFoundException e) {
        ApiException apiException = new ApiException(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now());
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }
}