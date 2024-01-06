package com.moneymong.global.exception.dto;


import java.util.List;
import lombok.Builder;

@Builder
public record ErrorResponses(Boolean result, Integer status, String code, List<String> messages) {
    public static ErrorResponses of(Integer status, String code, List<String> messages) {
        return ErrorResponses.builder()
                .result(false)
                .status(status)
                .code(code)
                .messages(messages)
                .build();
    }
}
