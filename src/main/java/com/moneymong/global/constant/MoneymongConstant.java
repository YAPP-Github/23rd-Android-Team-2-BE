package com.moneymong.global.constant;

import org.springframework.http.HttpStatus;

public class MoneymongConstant {
    public static Integer BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    public static Integer FORBIDDEN = HttpStatus.FORBIDDEN.value();
    public static Integer NOT_FOUND = HttpStatus.NOT_FOUND.value();
    public static Integer INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();
}
