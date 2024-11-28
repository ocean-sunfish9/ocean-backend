package com.sparta.oceanbackend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
    /**
     * 사용자 예외 처리
     * 공통(000), 게시글(100), 댓글(200) - 예외정의순서
     *
     * @since 2024-11-24
     */
    // 공통기능(C) 게시판(P)
    NAME_IN_USE("C001", "이미 사용 중인 사용자명입니다.", HttpStatus.CONFLICT),
    PASSWORD_MISMATCH("C002", "비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    NAME_MISMATCH("C003", "사용자명이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("C004", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    IN_VALID_REQUEST("C005","잘못된 요청 값", HttpStatus.BAD_REQUEST),
    TOO_MANY_REQUEST("C006","너무 많은 요청을 보냈습니다. 잠시 후 다시 시도해주세요.", HttpStatus.TOO_MANY_REQUESTS),
    NON_EXISTENT_POST("P001", "존재하지 않는 게시물입니다.", HttpStatus.BAD_REQUEST),
    NOT_WRITER_POST("P002","본인이 작성한 게시글이 아닙니다.", HttpStatus.BAD_REQUEST),
    NOT_ACTION_ALL_ALLOWED_BEST_POST("P003","본인이 작성한 게시글이 아닙니다.", HttpStatus.BAD_REQUEST),
    NON_EXISTENT_COMMENT("P004", "존재하지 않는 댓글입니다.", HttpStatus.BAD_REQUEST),
    NOT_WRITER_COMMENT("P005", "본인이 작성한 댓글이 아닙니다.", HttpStatus.BAD_REQUEST);
    private final String errorCode;
    private final String message;
    private final HttpStatus status;
}
