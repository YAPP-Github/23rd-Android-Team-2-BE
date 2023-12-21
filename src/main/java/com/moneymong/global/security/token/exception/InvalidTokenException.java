package com.moneymong.global.security.token.exception;

import com.moneymong.global.exception.custom.BusinessException;
import com.moneymong.global.exception.enums.ErrorCode;

public class InvalidTokenException extends BusinessException {
    public InvalidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
