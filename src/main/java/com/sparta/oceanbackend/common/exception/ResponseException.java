package com.sparta.oceanbackend.common.exception;

import lombok.Getter;

@Getter
public class ResponseException extends RuntimeException {

    private final String errorCode;
    private final String message;
    private final int status;

    public ResponseException(ExceptionType type) {
        this.errorCode = type.getErrorCode();
        this.message = type.getMessage();
        this.status = type.getStatus().value();
    }
}
