package org.sopt.exception;

public class InvalidBoardTypeException extends BusinessException {
    public InvalidBoardTypeException() {
        super(ErrorCode.INVALID_BOARD_TYPE);
    }
}
