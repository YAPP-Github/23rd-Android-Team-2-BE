package com.moneymong.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiResponse<T> {

    private Result result;
    private T data;
    private String message;
    private String errorCode;

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .result(Result.SUCCESS)
                .data(data)
                .message(message)
                .build();
    }

    public static ApiResponse<Void> fail(String message, String errorCode) {
        return ApiResponse.<Void>builder()
                .result(Result.FAIL)
                .message(message)
                .errorCode(errorCode)
                .build();
    }


    public static ApiResponse<Void> success() {
        return success(null, "");
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(data, null);
    }

    public static ApiResponse<Void> success(String message) {
        return success(null, message);
    }

    public static ApiResponse<Void> fail(String message) {
        return fail(message, null);
    }


    public enum Result {
        SUCCESS, FAIL
    }
}
