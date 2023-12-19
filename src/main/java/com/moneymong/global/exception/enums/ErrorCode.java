package com.moneymong.global.exception.enums;

import com.moneymong.global.constant.MoneymongConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(MoneymongConstant.BAD_REQUEST, "GLOBAL-400", "잘못된 요청입니다."),
    INTERNAL_SERVER(MoneymongConstant.INTERNAL_SERVER_ERROR, "GLOBAL-500", "서버 내부 오류입니다."),

    IMAGE_NOT_EXISTS(MoneymongConstant.NOT_FOUND, "IMAGE-001", "이미지를 찾을 수 없습니다."),
    FILE_IO_EXCEPTION(MoneymongConstant.INTERNAL_SERVER_ERROR, "IMAGE-002", "파일 생성에 실패했습니다.");

    private final Integer status;
    private final String code;
    private final String message;
}
