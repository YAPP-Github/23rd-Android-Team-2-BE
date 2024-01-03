package com.moneymong.global.constant;

import org.springframework.http.HttpStatus;

public class MoneymongConstant {
    /**
     * p2. 이걸 굳이 상수화를 할 필요가 있는지 의문이 듭니다.
     * HttpStatus 라는 상수가 이미 있는데 굳이 다시 변환 할 필요가 있을까요?
     * HttpStatus 객체를 다른 곳에 쓰지 않기 위함이라기에는 이미 쓰고있는 클래스가 소수이지만 보이네요.
     */
    public static Integer UNAUTHORIZED = HttpStatus.UNAUTHORIZED.value();
    public static Integer BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    public static Integer FORBIDDEN = HttpStatus.FORBIDDEN.value();
    public static Integer NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static Integer INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();

    // --- 장부 ---
    public static Integer MIN_ALLOWED_AMOUNT = 0;
    public static Integer MAX_ALLOWED_AMOUNT = 999_999_999;

}
