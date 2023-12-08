package com.moneymong.global.response;

import com.moneymong.global.exception.problem.Problem;
import com.moneymong.global.exception.problem.ProblemResponseData;
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
    private String message;
    private T data;
    private String errorCode;

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .result(Result.SUCCESS)
                .message(message)
                .data(data)
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
        return success("", null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return success(null, data);
    }

    public static ApiResponse<Void> success(String message) {
        return success(message, null);
    }

    public static ApiResponse<ProblemResponseData> fail(Problem cause, int showStackTraceCount) {
        return new ApiResponse<>(
                Result.FAIL,
                cause.getMessage(),
                ProblemResponseData.of(cause, showStackTraceCount),
                cause.getErrorCode().getCode()
        );
    }

    public static ApiResponse<Void> fail(String message) {
        return fail(message, null);
    }

    public static <T> ApiResponse<T> fail(Problem cause, T data) {
        return new ApiResponse<>(Result.FAIL, cause.getMessage(), data, cause.getErrorCode().getCode());
    }


    public enum Result {
        SUCCESS, FAIL
    }
}
