package com.moneymong.global.exception.enums;

import com.moneymong.global.constant.MoneymongConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(MoneymongConstant.BAD_REQUEST, "GLOBAL-400", "잘못된 요청입니다."),
    INTERNAL_SERVER(MoneymongConstant.INTERNAL_SERVER_ERROR, "GLOBAL-500", "서버 내부 오류입니다."),

    // ---- 유저 ---- //
    USER_NOT_FOUND(MoneymongConstant.NOT_FOUND, "USER-001", "존재하지 않는 회원입니다."),

    // ---- 장부 ---- //
    AGENCY_NOT_FOUND(MoneymongConstant.NOT_FOUND, "LEDGER-001", "소속에 가입 후 장부를 사용할 수 있습니다."),
    INVALID_LEDGER_ACCESS(MoneymongConstant.FORBIDDEN, "LEDGER-002", "유효하지 않은 접근입니다."),
    LEDGER_NOT_FOUND(MoneymongConstant.NOT_FOUND, "LEDGER-003", "장부가 존재하지 않습니다."),
    LEDGER_DETAIL_NOT_FOUND(MoneymongConstant.NOT_FOUND, "LEDGER-004", "장부 상세 내역이 존재하지 않습니다."),
    INVALID_LEDGER_AMOUNT(MoneymongConstant.BAD_REQUEST,"LEDGER-005", "0원 ~ 999,999,999원까지 기입 가능합니다."),

    // ---- 이미지 ---- //
    IMAGE_NOT_EXISTS(MoneymongConstant.NOT_FOUND, "IMAGE-001", "이미지를 찾을 수 없습니다."),
    FILE_IO_EXCEPTION(MoneymongConstant.INTERNAL_SERVER_ERROR, "IMAGE-002", "파일 생성에 실패했습니다.");

    private final Integer status;
    private final String code;
    private final String message;
}
