package org.sopt.exception;

public class LikeNotFoundException extends BusinessException {
    public LikeNotFoundException() {
        super(ErrorCode.LIKE_NOT_FOUND);
    }
}
