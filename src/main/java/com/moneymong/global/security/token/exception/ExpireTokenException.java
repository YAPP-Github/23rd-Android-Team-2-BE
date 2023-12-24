package com.moneymong.global.security.token.exception;

import com.moneymong.global.exception.enums.ErrorCode;

public class ExpireTokenException extends TokenException {
    public ExpireTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
