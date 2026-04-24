package org.sopt.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.sopt.domain.BoardType;
import org.sopt.dto.Response.ApiResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handlePostNotFound(PostNotFoundException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException exception) {
        ErrorCode errorCode = ErrorCode.INVALID_POST_REQUEST;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode.getCode(), exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        if (exception.getRequiredType() == BoardType.class) {
            ErrorCode errorCode = ErrorCode.INVALID_BOARD_TYPE;
            return ResponseEntity.status(errorCode.getStatus())
                    .body(ApiResponse.fail(errorCode.getCode(), errorCode.getMessage()));
        }

        ErrorCode errorCode = ErrorCode.INVALID_POST_REQUEST;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        if (exception.getCause() instanceof InvalidFormatException invalidFormatException
                && invalidFormatException.getTargetType() == BoardType.class) {
            ErrorCode errorCode = ErrorCode.INVALID_BOARD_TYPE;
            return ResponseEntity.status(errorCode.getStatus())
                    .body(ApiResponse.fail(errorCode.getCode(), errorCode.getMessage()));
        }

        ErrorCode errorCode = ErrorCode.INVALID_POST_REQUEST;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode.getCode(), errorCode.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception exception) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode.getCode(), errorCode.getMessage()));
    }
}
