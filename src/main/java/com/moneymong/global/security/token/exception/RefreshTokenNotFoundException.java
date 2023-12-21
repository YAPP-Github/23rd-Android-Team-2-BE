package com.moneymong.global.security.token.exception;

import com.moneymong.global.exception.custom.BusinessException;
import com.moneymong.global.exception.enums.ErrorCode;

public class RefreshTokenNotFoundException extends BusinessException {
    public RefreshTokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
