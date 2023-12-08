package com.moneymong.common.exception.problem;

public enum ErrorCategory {
    /**
     * 유저의 요청이 정상적이지 않은 경우 사용합니다.
     */
    INVALID_REQUEST,

    /**
     * 인증/인가 자체가 유효하지 않은 경우 사용합니다.
     */
    UNAUTHORIZED,

    /**
     * 인증 자체는 성공적이나, 권한이 존재하지 않는 경우 사용합니다.
     */
    FORBIDDEN,

    /**
     * 명령을 처리할 개체가 존재하지 않는 경우 사용합니다.
     */
    NOT_FOUND,

    /**
     * 유저의 요청도 정상적이고 명령을 처리할 개체도 존재하지만, 비즈니스 로직 상 유저의 요청을 완료할 수 없는 경우 사용합니다.
     */
    UNPROCESSABLE,

    /**
     * 서비스가 맛이 간 경우 사용합니다.
     */
    SERVICE_UNAVAILABLE,
}
