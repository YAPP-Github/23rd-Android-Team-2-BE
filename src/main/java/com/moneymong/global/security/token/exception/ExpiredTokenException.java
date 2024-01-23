package com.moneymong.global.security.token.exception;

import com.moneymong.global.exception.enums.ErrorCode;

public class ExpiredTokenException extends TokenException {
    public ExpiredTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
