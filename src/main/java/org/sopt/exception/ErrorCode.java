package org.sopt.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_001", "해당 게시글을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "해당 사용자를 찾을 수 없습니다."),
    DUPLICATE_LIKE(HttpStatus.CONFLICT, "LIKE_001", "이미 좋아요를 누른 게시글입니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "LIKE_002", "좋아요를 누르지 않은 게시글입니다."),
    INVALID_POST_REQUEST(HttpStatus.BAD_REQUEST, "POST_002", "게시글 입력값이 올바르지 않습니다."),
    INVALID_BOARD_TYPE(HttpStatus.BAD_REQUEST, "POST_003", "게시판 종류가 올바르지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_001", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
