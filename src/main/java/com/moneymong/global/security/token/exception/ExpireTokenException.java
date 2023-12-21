package com.moneymong.global.security.token.exception;

import com.moneymong.global.exception.custom.BusinessException;
import com.moneymong.global.exception.enums.ErrorCode;

public class ExpireTokenException extends BusinessException {
    public ExpireTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
