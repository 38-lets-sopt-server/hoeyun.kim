// 유효하지 않은 게시판 타입 요청을 나타내는 예외
package org.sopt.exception;

public class InvalidBoardTypeException extends BusinessException {
    public InvalidBoardTypeException() {
        super(ErrorCode.INVALID_BOARD_TYPE);
    }
}
