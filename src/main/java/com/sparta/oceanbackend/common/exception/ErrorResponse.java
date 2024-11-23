package com.sparta.oceanbackend.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
  private String errorCode;
  private String message;
  private int status;
  private long timestamp;

  public ErrorResponse(String errorCode, String message, int status) {
    this.errorCode = errorCode;
    this.message = message;
    this.status = status;
    this.timestamp = System.currentTimeMillis();
  }
}
