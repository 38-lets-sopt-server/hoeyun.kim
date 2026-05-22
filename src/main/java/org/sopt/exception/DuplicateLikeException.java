package org.sopt.exception;

public class DuplicateLikeException extends RuntimeException {
    private final ErrorCode errorCode;

    public DuplicateLikeException() {
        super(ErrorCode.DUPLICATE_LIKE.getMessage());
        this.errorCode = ErrorCode.DUPLICATE_LIKE;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
