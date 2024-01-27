package com.moneymong.global.exception.enums;

import com.moneymong.global.constant.MoneymongConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BAD_REQUEST(MoneymongConstant.BAD_REQUEST, "GLOBAL-400", "잘못된 요청입니다."),
    ACCESS_DENIED(MoneymongConstant.FORBIDDEN, "GLOBAL-403", "접근 권한이 없습니다."),
    INTERNAL_SERVER(MoneymongConstant.INTERNAL_SERVER_ERROR, "GLOBAL-500", "서버 내부 오류입니다."),

    // ---- 유저 ---- //
    USER_NOT_FOUND(MoneymongConstant.NOT_FOUND, "USER-001", "존재하지 않는 회원입니다."),

    // ---- 대학교 ---- //
    USER_UNIVERSITY_NOT_FOUND(MoneymongConstant.NOT_FOUND, "UNIVERSITY-001", "회원의 대학 정보가 존재하지 않습니다."),
    USER_UNIVERSITY_ALREADY_EXISTS(MoneymongConstant.BAD_REQUEST, "UNIVERSITY-002", "회원의 대학 정보가 이미 존재합니다."),

    // ---- 소속 멤버 ---- //
    AGENCY_USER_NOT_FOUND(MoneymongConstant.NOT_FOUND, "AGENCY-USER-001", "소속에 유저가 존재하지 않습니다."),
    INVALID_AGENCY_USER_ACCESS(MoneymongConstant.FORBIDDEN, "AGENCY-USER-002", "유효하지 않은 접근입니다."),
    BLOCKED_AGENCY_USER(MoneymongConstant.FORBIDDEN, "AGENCY-USER-003", "소속에서 강제퇴장된 유저입니다."),
    ALREADY_EXIST_AGENCY_USER(MoneymongConstant.BAD_REQUEST, "AGENCY-USER-004", "이미 가입된 유저입니다."),

    // ---- 장부 ---- //
    AGENCY_NOT_FOUND(MoneymongConstant.NOT_FOUND, "LEDGER-001", "소속에 가입 후 장부를 사용할 수 있습니다."),
    INVALID_LEDGER_ACCESS(MoneymongConstant.FORBIDDEN, "LEDGER-002", "유효하지 않은 접근입니다."),
    LEDGER_NOT_FOUND(MoneymongConstant.NOT_FOUND, "LEDGER-003", "장부가 존재하지 않습니다."),
    LEDGER_DETAIL_NOT_FOUND(MoneymongConstant.NOT_FOUND, "LEDGER-004", "장부 상세 내역이 존재하지 않습니다."),
    LEDGER_RECEIPT_NOT_FOUND(MoneymongConstant.NOT_FOUND, "LEDGER-006", "장부 영수증 내역이 존재하지 않습니다."),
    INVALID_LEDGER_AMOUNT(MoneymongConstant.BAD_REQUEST,"LEDGER-005", "0원 ~ 999,999,999원까지 기입 가능합니다."),
    LEDGER_DOCUUMENT_NOT_FOUND(MoneymongConstant.NOT_FOUND, "LEDGER-007", "장부 증빙 자료 내역이 존재하지 않습니다."),

    // ---- 이미지 ---- //
    IMAGE_NOT_EXISTS(MoneymongConstant.NOT_FOUND, "IMAGE-001", "이미지를 찾을 수 없습니다."),
    FILE_IO_EXCEPTION(MoneymongConstant.INTERNAL_SERVER_ERROR, "IMAGE-002", "파일 생성에 실패했습니다."),

    // ---- 토큰 ---- //
    INVALID_TOKEN(MoneymongConstant.UNAUTHORIZED, "TOKEN-001", "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(MoneymongConstant.UNAUTHORIZED, "TOKEN-002", "만료된 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(MoneymongConstant.NOT_FOUND, "TOKEN-003", "리프레시 토큰을 찾을 수 없습니다."),
    REFRESH_TOKEN_EXPIRED(MoneymongConstant.UNAUTHORIZED, "TOKEN-004", "만료된 리프레시 토큰입니다."),

    // ---- 로그인 ---- //
    INVALID_PROVIDER(MoneymongConstant.BAD_REQUEST, "LOGIN-001", "유효하지 않은 로그인 수단입니다."),

    // ---- 초대코드 ---- //
    INVITATION_CODE_NOT_FOUND(MoneymongConstant.NOT_FOUND, "INVITATION-001", "초대코드가 존재하지 않습니다."),
    INVITATION_CODE_NOT_CERTIFIED_USER(MoneymongConstant.FORBIDDEN, "INVITATION-002", "초대코드 미인증 유저입니다."),

    // ---- 네트워크 ---- //
    HTTP_CLIENT_REQUEST_FAILED(MoneymongConstant.INTERNAL_SERVER_ERROR, "NETWORK-001", "서버 요청에 실패하였습니다.");

    private final Integer status;
    private final String code;
    private final String message;
}
