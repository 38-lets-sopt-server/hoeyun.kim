package org.sopt.dto.Response;

public class commonResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;

    private commonResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> commonResponse<T> success(String message, T data) {
        return new commonResponse<>(true, message, data);
    }

    public static <T> commonResponse<T> fail(String message) {
        return new commonResponse<>(false, message, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
