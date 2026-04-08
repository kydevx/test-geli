package com.example.test_geli.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.test_geli.aspect.response.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @Response
    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @Response
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(500)
                .body( "Internal Server Error: " + ex.getMessage());
    }
}
