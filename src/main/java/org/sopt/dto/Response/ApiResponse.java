package org.sopt.dto.Response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "공통 API 응답")
public class ApiResponse<T> {
    @Schema(description = "요청 성공 여부", example = "true")
    private final boolean success;

    @Schema(description = "응답 코드", example = "COMMON_200")
    private final String code;

    @Schema(description = "응답 메시지", example = "요청이 성공했습니다.")
    private final String message;

    @Schema(description = "응답 데이터")
    private final T data;

    private ApiResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(String code, String message, T data) {
        return new ApiResponse<>(true, code, message, data);
    }

    public static <T> ApiResponse<T> fail(String code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
