package com.moneymong.global.exception.enums;

import com.moneymong.global.constant.MoneymongConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(MoneymongConstant.BAD_REQUEST, "GLOBAL-400", "잘못된 요청입니다."),
    INTERNAL_SERVER(MoneymongConstant.INTERNAL_SERVER_ERROR, "GLOBAL-500", "서버 내부 오류입니다.");

    private final Integer status;
    private final String code;
    private final String message;
}
