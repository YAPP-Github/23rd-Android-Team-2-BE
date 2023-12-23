package com.moneymong.global.security.token.exception;

import com.moneymong.global.exception.enums.ErrorCode;

public class InvalidTokenException extends TokenException {
    public InvalidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
