package com.waterfy.projeto.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiException {
    private final String message;
    private final Integer code;
    private final HttpStatus httpStatus;
    private final ZonedDateTime zonedDateTime;
}
