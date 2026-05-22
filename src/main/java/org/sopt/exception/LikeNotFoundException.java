package org.sopt.exception;

public class LikeNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public LikeNotFoundException() {
        super(ErrorCode.LIKE_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.LIKE_NOT_FOUND;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
