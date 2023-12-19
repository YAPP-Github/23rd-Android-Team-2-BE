package com.moneymong.global.exception.dto;

import com.moneymong.global.exception.enums.ErrorCode;
import lombok.Builder;

@Builder
public record ErrorResponse(Boolean result, Integer status, String code, String message) {
    public static ErrorResponse from(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .result(false)
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}
