package com.moneymong.common.exception.problem;

import lombok.Getter;

/**
 * code: 사전에 합의한 code (ex: "user/user-not-found")
 * errorCategory: status code를 결정하는 값
 */

@Getter
public final class ErrorCode {
    private final String code;
    private final ErrorCategory errorCategory;

    private ErrorCode(String code, ErrorCategory errorCategory) {
        this.code = code;
        this.errorCategory = errorCategory;
    }

    public static ErrorCode of(String code, ErrorCategory category) {
        return new ErrorCode(code, category);
    }
}
