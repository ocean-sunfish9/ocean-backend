package com.sparta.oceanbackend.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ErrorResponse> handleResponseException(ResponseException e) {
        return buildErrorResponse(e);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(ResponseException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getErrorCode(),e.getMessage(),e.getStatus().value()
        );
        return new ResponseEntity<>(errorResponse,e.getStatus());
    }

}
