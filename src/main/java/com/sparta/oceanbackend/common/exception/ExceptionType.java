package com.sparta.oceanbackend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    /**
     * 사용자 예외 처리
     * 가게(S) - 공통(000), 사장(100), 고객(200) - 예외정의순서
     *
     * @since 2024-11-06
     */
    // 공통기능(C)
    NAME_IN_USE("C001", "이미 사용 중인 사용자명입니다.", HttpStatus.CONFLICT);

    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
