package com.sparta.oceanbackend.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ErrorResponse> handleResponseException(ResponseException e) {
        return buildErrorResponse(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ExceptionType.IN_VALID_REQUEST.getErrorCode(),
                                                                                    e.getBindingResult().getAllErrors().get(0).getDefaultMessage(),
                                                                                    ExceptionType.IN_VALID_REQUEST.getStatus().value()));
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(ResponseException e) {
        ErrorResponse errorResponse = new ErrorResponse(
            e.getErrorCode(),
            e.getMessage(),
            e.getStatus().value()
        );
        return new ResponseEntity<>(errorResponse,
                                    e.getStatus());
    }
}
