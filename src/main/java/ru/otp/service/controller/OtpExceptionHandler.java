package ru.otp.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class OtpExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badCredentialsExceptionException(BadCredentialsException e) {
        log.debug(e.getMessage(), e);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        log.debug(e.getMessage(), e);

        return ResponseEntity.ok(e.getMessage());
    }

}
