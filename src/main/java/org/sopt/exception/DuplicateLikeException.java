package org.sopt.exception;

public class DuplicateLikeException extends BusinessException {
    public DuplicateLikeException() {
        super(ErrorCode.DUPLICATE_LIKE);
    }
}
