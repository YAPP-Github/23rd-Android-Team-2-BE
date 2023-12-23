package com.moneymong.global.security.token.exception;

import com.moneymong.global.exception.enums.ErrorCode;

public class RefreshTokenNotFoundException extends TokenException {
    public RefreshTokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
